import React, { useState, useEffect } from "react";
import styles from "../../styles/teams.module.css";
import RoleSelector from "./RoleSelector";

const TeamView = ({ team, isAdmin, onDeleteTeam }) => {
  const [showMembers, setShowMembers] = useState(false);
  const [selectedMembers, setSelectedMembers] = useState([]);
  const [teamMembers, setTeamMembers] = useState([]);

  useEffect(() => {
    if (team?.users && team?.teamManagers) {
      const members = team.users.map((user) => {
        // Check if the user is a manager and assign the role accordingly
        const isManager = team.teamManagers.some((manager) => manager.id === user.id);
        return {
          id: user.id,
          name: `${user.firstName} ${user.lastName}`,
          role: isManager ? "Manager" : "Member",
        };
      });
      setTeamMembers(members); // Update team members state
    }
  }, [team]); // This will run every time `team` changes

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

  const handleDeleteMembers = () => {
    setTeamMembers((prevMembers) =>
      prevMembers.filter((member) => !selectedMembers.includes(member.id))
    );
    setSelectedMembers([]);
  };

  const handleAssignRole = (role) => {
    if (role === "Admin") {
      console.log("Cannot assign Admin role.");
      return;
    }

    setTeamMembers((prevMembers) =>
      prevMembers.map((member) => {
        if (selectedMembers.includes(member.id)) {
          return { ...member, role };
        }
        return member;
      })
    );

    setSelectedMembers([]);
  };

  const handleDeleteTeam = () => {
    onDeleteTeam(team.name); // Callback function to handle the deletion of the team
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
              <RoleSelector onAssignRole={handleAssignRole} currentRole="Member" />
              <button
                className={styles.deleteMemberButton}
                onClick={handleDeleteMembers}
              >
                Delete Selected Members
              </button>
            </div>
          )}
        </div>
      )}
    </div>
  );
};

export default TeamView;
