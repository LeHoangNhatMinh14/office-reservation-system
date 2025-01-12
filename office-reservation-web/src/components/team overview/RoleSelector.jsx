import React, { useState, useEffect } from "react";
import styles from "../../styles/teams.module.css";

// Component to Assign Roles to Team Members
const RoleSelector = ({ onAssignRole, currentRole }) => {
  // State to track the selected role, initialized to "Member"
  const [selectedRole, setSelectedRole] = useState(currentRole || "Member");

  // Update the selected role when currentRole prop changes
  useEffect(() => {
    setSelectedRole(currentRole || "Member"); // Ensure a fallback value
  }, [currentRole]);

  // Function to handle the change in role selection from the dropdown
  const handleRoleChange = (event) => {
    setSelectedRole(event.target.value); // Update the selected role based on user input
  };

  // Function to handle the assignment of the selected role
  const handleAssign = () => {
    onAssignRole(selectedRole); // Pass the selected role to the parent component via the callback function
  };

  return (
    <div className={styles.roleSelector}>
      {/* Dropdown to select a role */}
      <select
        value={selectedRole} // Ensure `value` is never null
        onChange={handleRoleChange}
        className={styles.roleDropdown}
      >
        <option value="Member">Member</option>
        <option value="Manager">Manager</option>
      </select>
      {/* Button to assign the selected role */}
      <button className={styles.assignButton} onClick={handleAssign}>
        Assign Role
      </button>
    </div>
  );
};

export default RoleSelector;
