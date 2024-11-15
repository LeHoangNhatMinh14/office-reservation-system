import React, { useState } from "react";

// Component for Assigning Roles
const RoleSelector = ({ onAssignRole }) => {
  const [selectedRole, setSelectedRole] = useState("User");

  const roles = ["User", "Moderator", "Admin"];

  const handleRoleChange = (e) => {
    setSelectedRole(e.target.value);
  };

  const handleAssign = () => {
    onAssignRole(selectedRole);
  };

  return (
    <div className="role-selector">
      <select value={selectedRole} onChange={handleRoleChange}>
        {roles.map((role, index) => (
          <option key={index} value={role}>{role}</option>
        ))}
      </select>
      <button onClick={handleAssign}>Assign Role</button>
    </div>
  );
};

export default RoleSelector;
