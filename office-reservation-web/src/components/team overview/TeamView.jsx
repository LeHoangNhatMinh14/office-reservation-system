import React, { useState, useEffect } from "react";
import styles from "../../styles/teams.module.css";
import RoleSelector from "./RoleSelector";
import TeamCalls from "../api calls/TeamCalls"
import AddMemberToTeam from "./AddMemberToTeam";

const TeamView = ({ team, isAdmin, onDeleteTeam }) => {
  const [showMembers, setShowMembers] = useState(false);
  const [selectedMembers, setSelectedMembers] = useState([]);
  const [teamMembers, setTeamMembers] = useState([]);
  const [showAddMemberModal, setShowAddMemberModal] = useState(false);

  useEffect(() => {
    if (team?.users && team?.teamManagers) {
      // Combine users and managers into a single list with roles
      const usersWithRoles = team.users.map((user) => ({
        id: user.id,
        name: `${user.firstName} ${user.lastName}`,
        role: "Member",
      }));
  
      const managersWithRoles = team.teamManagers
        .filter((manager) => !team.users.some((user) => user.id === manager.id)) // Avoid duplicate entries
        .map((manager) => ({
          id: manager.id,
          name: `${manager.firstName} ${manager.lastName}`,
          role: "Manager",
        }));
  
      // Combine the two lists
      const combinedMembers = [...usersWithRoles, ...managersWithRoles];
  
      setTeamMembers(combinedMembers); // Update team members state
    }
  }, [team]);  

  const toggleShowMembers = () => {
    setShowMembers(!showMembers);
  };

  const handleMemberSelect = (memberId) => {
    setSelectedMembers((prevSelected) => {
      if (prevSelected.includes(memberId)) {
        return prevSelected.filter((m) => m !== memberId);
      } else {
        return [...prevSelected, memberId];
      }
    });
  };

  const handleDeleteMembers = async () => {
    try {
      // Clone the current team data
      const updatedTeam = {
        ...team,
        users: team.users.filter((user) => !selectedMembers.includes(user.id)),
        teamManagers: team.teamManagers.filter(
          (manager) => !selectedMembers.includes(manager.id)
        ),
      };
  
      // Call the API to update the team with the updated lists
      await TeamCalls.updateTeam(team.id, updatedTeam);
  
      // Update the local state to reflect the changes
      const updatedMembers = updatedTeam.users.concat(
        updatedTeam.teamManagers.map((manager) => ({
          id: manager.id,
          name: `${manager.firstName} ${manager.lastName}`,
          role: "Manager",
        }))
      ).map((user) => ({
        id: user.id,
        name: `${user.firstName} ${user.lastName}`,
        role: updatedTeam.teamManagers.some((manager) => manager.id === user.id)
          ? "Manager"
          : "Member",
      }));
  
      setTeamMembers(updatedMembers);
      setSelectedMembers([]); // Clear the selection
    } catch (error) {
      console.error("Error updating team:", error);
    }
  };
  

  const handleAssignRole = async (role) => {
    if (selectedMembers.length !== 1) {
      console.error("Please select exactly one member to assign a role.");
      return;
    }
  
    const memberId = selectedMembers[0]; // Get the selected member's ID
    const updatedUsers = [...team.users];
    const updatedManagers = [...team.teamManagers];
  
    // Find the selected member's current role and update lists accordingly
    const memberIndexInUsers = updatedUsers.findIndex((user) => user.id === memberId);
    const memberIndexInManagers = updatedManagers.findIndex((manager) => manager.id === memberId);
  
    if (role === "Manager") {
      if (memberIndexInUsers !== -1) {
        // Remove from users and add to managers
        const [user] = updatedUsers.splice(memberIndexInUsers, 1);
        updatedManagers.push(user);
      }
    } else if (role === "Member") {
      if (memberIndexInManagers !== -1) {
        // Remove from managers and add to users
        const [manager] = updatedManagers.splice(memberIndexInManagers, 1);
        updatedUsers.push(manager);
      }
    }
  
    // Update the team object
    const updatedTeam = {
      ...team,
      users: updatedUsers,
      teamManagers: updatedManagers,
    };
  
    try {
      // Persist changes to the backend
      await TeamCalls.updateTeam(team.id, updatedTeam);
      // Update local state to reflect changes
      setTeamMembers(
        updatedUsers.map((user) => ({
          id: user.id,
          name: `${user.firstName} ${user.lastName}`,
          role: "Member",
        }))
        .concat(
          updatedManagers.map((manager) => ({
            id: manager.id,
            name: `${manager.firstName} ${manager.lastName}`,
            role: "Manager",
          }))
        )
      );
      setSelectedMembers([]); // Clear selection
    } catch (error) {
      console.error("Error assigning role:", error);
    }
  };

  const handleDeleteTeam = async () => {
    try {
      await TeamCalls.deleteTeam(team.id); // Call API to delete the team
      onDeleteTeam(team.name); // Callback to update parent component
    } catch (error) {
      console.error('Error deleting team:', error);
    }
  };

  const handleMemberAdded = (newMember) => {
    setTeamMembers((prevMembers) => [
      ...prevMembers,
      { id: newMember.id, name: `${newMember.firstName} ${newMember.lastName}`, role: "Member" },
    ]);
  };

  
  return (
    <div className={styles.teamView}>
      <div onClick={toggleShowMembers} className={styles.teamHeader}>
        <span className={styles.teamName}>
          {team.name} <span>{showMembers ? "v" : ">"}</span>
        </span>
        {isAdmin && (
          <button
            className={styles.deleteTeamButton}
            onClick={(e) => {
              e.stopPropagation(); // Prevent toggle when clicking delete
              handleDeleteTeam();
            }}
          >
            Delete Team
          </button>
        )}
      </div>
      {showMembers && (
        <div className={styles.teamMembers}>
          {teamMembers.length === 0 ? (
            <p>No team members available</p>
          ) : (
            teamMembers.map((member) => (
              <div
                key={member.id}
                className={`${styles.teamMemberBox} ${
                  selectedMembers.includes(member.id) ? styles.selectedMember : ""
                }`}
                onClick={() => handleMemberSelect(member.id)}
              >
                {member.name} - {member.role}
              </div>
            ))
          )}
          {isAdmin && (
            <div className={styles.teamActions}>
              <RoleSelector
                onAssignRole={handleAssignRole}
                currentRole={
                  selectedMembers.length === 1
                    ? teamMembers.find((member) => member.id === selectedMembers[0])?.role
                    : null
                }
              />
              <div className={styles.actionButtons}>
              <button
                className={styles.deleteMemberButton}
                onClick={handleDeleteMembers}
                disabled={selectedMembers.length === 0}
              >
                Delete Selected Members
              </button>
              <button
                className={styles.newMemberTeamButton}
                onClick={() => setShowAddMemberModal(true)}
              >
                Add Member
              </button>
              <AddMemberToTeam
                team={team}
                show={showAddMemberModal}
                onClose={() => setShowAddMemberModal(false)}
                onMemberAdded={handleMemberAdded}
              />
              </div>
            </div>
          )}
        </div>
      )}
    </div>
  );
};

export default TeamView;
