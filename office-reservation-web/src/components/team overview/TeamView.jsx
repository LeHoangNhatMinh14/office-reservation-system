import React, { useState } from "react";
import styles from "../../styles/teams.module.css";
import RoleSelector from "./RoleSelector";
import TeamMemberBox from "./TeamMemberBox";

// Component to Show Individual Team Details
const TeamView = ({ team, isAdmin }) => {
  const [showMembers, setShowMembers] = useState(false);
  const [selectedMembers, setSelectedMembers] = useState([]);
  const [teamMembers, setTeamMembers] = useState(
    team.members.map((member) => ({
      name: member,
      role: "Member", // Set the default role as "Member"
    }))
  );

  const toggleShowMembers = () => {
    setShowMembers(!showMembers);
  };

  const handleMemberSelect = (memberName) => {
    setSelectedMembers((prevSelected) => {
      if (prevSelected.includes(memberName)) {
        return prevSelected.filter((m) => m !== memberName);
      } else {
        return [...prevSelected, memberName];
      }
    });
  };

  const handleDeleteMembers = () => {
    console.log("Deleting members: ", selectedMembers);
    setTeamMembers((prevMembers) =>
      prevMembers.filter((member) => !selectedMembers.includes(member.name))
    );
    setSelectedMembers([]); // Clear selected members after deletion
  };

  const handleAssignRole = (role) => {
    if (role === "Admin") {
      console.log("Cannot assign Admin role.");
      return;
    }

    // Assign role to selected members
    setTeamMembers((prevMembers) =>
      prevMembers.map((member) => {
        if (selectedMembers.includes(member.name)) {
          return { ...member, role };
        }
        return member;
      })
    );

    console.log("Assigning role ", role, " to members: ", selectedMembers);
    setSelectedMembers([]); // Clear selected members after role assignment
  };

  return (
    <div className={styles.teamView}>
      <div className={styles.teamHeader} onClick={toggleShowMembers}>
        {team.name} <span>{showMembers ? "v" : ">"}</span>
      </div>
      {showMembers && (
        <div className={styles.teamMembers}>
          {teamMembers.map((member, index) => (
            <TeamMemberBox
              key={index}
              member={member}
              isSelected={selectedMembers.includes(member.name)}
              onSelect={handleMemberSelect}
            />
          ))}
          {isAdmin && (
            <div className={styles.teamActions}>
              <button className={styles.deleteMemberButton} onClick={handleDeleteMembers}>Delete Selected Members</button>
              <RoleSelector onAssignRole={handleAssignRole} />
            </div>
          )}
        </div>
      )}
    </div>
  );
};

export default TeamView;
