import React, { useState } from "react";
import RoleSelector from "./RoleSelector";

// Component to Show Individual Team Details
const TeamView = ({ team }) => {
  const [showMembers, setShowMembers] = useState(false);
  const [selectedMembers, setSelectedMembers] = useState([]);

  const toggleShowMembers = () => {
    setShowMembers(!showMembers);
  };

  const handleMemberSelect = (member) => {
    setSelectedMembers((prevSelected) => {
      if (prevSelected.includes(member)) {
        return prevSelected.filter((m) => m !== member);
      } else {
        return [...prevSelected, member];
      }
    });
  };

  const handleDeleteMembers = () => {
    // Uncomment and modify to make backend call for deleting members
    // fetch('/deleteMembers', {
    //   method: 'POST',
    //   body: JSON.stringify(selectedMembers),
    //   headers: {
    //     'Content-Type': 'application/json'
    //   }
    // }).then(response => response.json()).then(data => {
    //   console.log('Deleted:', data);
    // });
    console.log("Deleting members: ", selectedMembers);
  };

  const handleAssignRole = (role) => {
    // Uncomment and modify to make backend call for assigning roles
    // fetch('/assignRole', {
    //   method: 'POST',
    //   body: JSON.stringify({ members: selectedMembers, role }),
    //   headers: {
    //     'Content-Type': 'application/json'
    //   }
    // }).then(response => response.json()).then(data => {
    //   console.log('Role Assigned:', data);
    // });
    console.log("Assigning role ", role, " to members: ", selectedMembers);
  };

  return (
    <div className="team-view">
      <div className="team-header" onClick={toggleShowMembers}>
        {team.name} <span>{showMembers ? "v" : ">"}</span>
      </div>
      {showMembers && (
        <div className="team-members">
          {team.members.map((member, index) => (
            <div key={index} className="team-member">
              <input
                type="checkbox"
                onChange={() => handleMemberSelect(member)}
                checked={selectedMembers.includes(member)}
              />
              {member}
            </div>
          ))}
          <div className="team-actions">
            <button onClick={handleDeleteMembers}>Delete Selected Members</button>
            <RoleSelector onAssignRole={handleAssignRole} />
          </div>
        </div>
      )}
    </div>
  );
};

export default TeamView;
