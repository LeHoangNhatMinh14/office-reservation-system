import React, { useState, useEffect } from "react";
import styles from "../../styles/teams.module.css";
import RoleSelector from "./RoleSelector";
import TeamCalls from "../api calls/TeamCalls";
import AddMemberToTeam from "./AddMemberToTeam";

const TeamView = ({ team, isAdmin, onDeleteTeam }) => {
  const [showMembers, setShowMembers] = useState(false);
  const [selectedMembers, setSelectedMembers] = useState([]);
  const [teamMembers, setTeamMembers] = useState([]);
  const [showAddMemberModal, setShowAddMemberModal] = useState(false);

  useEffect(() => {
    if (team?.users && team?.teamManagers) {
      const usersWithRoles = team.users.map((user) => ({
        id: user.id,
        name: `${user.firstName} ${user.lastName}`,
        role: "Member",
      }));

      const managersWithRoles = team.teamManagers
        .filter((manager) => !team.users.some((user) => user.id === manager.id))
        .map((manager) => ({
          id: manager.id,
          name: `${manager.firstName} ${manager.lastName}`,
          role: "Manager",
        }));

      setTeamMembers([...usersWithRoles, ...managersWithRoles]);
    }
  }, [team]);

  const toggleShowMembers = () => setShowMembers(!showMembers);

  const handleMemberSelect = (memberId) => {
    setSelectedMembers((prevSelected) =>
      prevSelected.includes(memberId)
        ? prevSelected.filter((id) => id !== memberId)
        : [...prevSelected, memberId]
    );
  };

  const handleDeleteMembers = async () => {
    try {
      const updatedTeam = {
        ...team,
        users: team.users.filter((user) => !selectedMembers.includes(user.id)),
        teamManagers: team.teamManagers.filter(
          (manager) => !selectedMembers.includes(manager.id)
        ),
      };

      await TeamCalls.updateTeam(team.id, updatedTeam);

      setTeamMembers(
        updatedTeam.users
          .map((user) => ({
            id: user.id,
            name: `${user.firstName} ${user.lastName}`,
            role: "Member",
          }))
          .concat(
            updatedTeam.teamManagers.map((manager) => ({
              id: manager.id,
              name: `${manager.firstName} ${manager.lastName}`,
              role: "Manager",
            }))
          )
      );

      setSelectedMembers([]);
    } catch (error) {
      console.error("Error updating team:", error);
    }
  };

  const handleAssignRole = async (role) => {
    if (selectedMembers.length !== 1) {
      alert("Please select exactly one member to assign a role.");
      return;
    }

    const memberId = selectedMembers[0];
    const updatedUsers = [...team.users];
    const updatedManagers = [...team.teamManagers];

    if (role === "Manager") {
      const index = updatedUsers.findIndex((user) => user.id === memberId);
      if (index > -1) {
        const [user] = updatedUsers.splice(index, 1);
        updatedManagers.push(user);
      }
    } else if (role === "Member") {
      const index = updatedManagers.findIndex(
        (manager) => manager.id === memberId
      );
      if (index > -1) {
        const [manager] = updatedManagers.splice(index, 1);
        updatedUsers.push(manager);
      }
    }

    const updatedTeam = { ...team, users: updatedUsers, teamManagers: updatedManagers };

    try {
      await TeamCalls.updateTeam(team.id, updatedTeam);
      setTeamMembers(
        updatedUsers
          .map((user) => ({
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
      setSelectedMembers([]);
    } catch (error) {
      console.error("Error assigning role:", error);
    }
  };

  const handleDeleteTeam = async () => {
    try {
      await TeamCalls.deleteTeam(team.id);
      onDeleteTeam(team.name);
    } catch (error) {
      console.error("Error deleting team:", error);
    }
  };

  const handleMemberAdded = (newMember) => {
    setTeamMembers((prev) => [
      ...prev,
      { id: newMember.id, name: `${newMember.firstName} ${newMember.lastName}`, role: "Member" },
    ]);
  };

  return (
    <div className={styles.teamView}>
      <div onClick={toggleShowMembers} className={styles.teamHeader}>
        <span className={styles.teamName}>
          {team.name} {showMembers ? "v" : ">"}
        </span>
        {isAdmin && (
          <button
            className={styles.deleteTeamButton}
            onClick={(e) => {
              e.stopPropagation();
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
                  selectedMembers.includes(member.id)
                    ? styles.selectedMember
                    : ""
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
            </div>
          )}
          {showAddMemberModal && (
            <AddMemberToTeam
              team={team}
              show={showAddMemberModal}
              onClose={() => setShowAddMemberModal(false)}
              onMemberAdded={handleMemberAdded}
            />
          )}
        </div>
      )}
    </div>
  );
};

export default TeamView;
