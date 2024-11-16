import React from "react";
import styles from "../../styles/teams.module.css";

const TeamMemberBox = ({ member, isSelected, onSelect }) => {
  return (
    <div
      className={`${styles.teamMemberBox} ${isSelected ? styles.selectedMember : ""}`}
      onClick={() => onSelect(member.name)}
    >
      {member.name} - {member.role}
    </div>
  );
};

export default TeamMemberBox;
