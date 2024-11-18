import React, { useState } from "react";
import styles from "../../styles/teamsAdd.module.css";

const AddTeam = ({ onClose, onAddTeam }) => {
  // Sample list of people to choose from
  const [people] = useState([
    "Alice", "Bob", "Charlie", "David", "Eve", "Frank", "Grace"
  ]);

  // State to track selected members
  const [selectedMembers, setSelectedMembers] = useState([]);
  
  // Function to handle form submission
  const handleAddTeam = (event) => {
    event.preventDefault();
    const newTeam = {
      name: event.target.teamName.value,
      members: selectedMembers,
    };
    onAddTeam(newTeam); // Add the team using parent function
    onClose(); // Close modal after adding the team
  };

  // Function to handle selecting members by clicking on their box
  const handleMemberSelect = (member) => {
    setSelectedMembers((prevSelected) => {
      if (prevSelected.includes(member)) {
        return prevSelected.filter((m) => m !== member); // Remove if already selected
      } else {
        return [...prevSelected, member]; // Add if not already selected
      }
    });
  };

  return (
    <div className={styles.modalBackdrop}>
      <div className={styles.addTeamModal}>
        <button className={styles.closeButton} onClick={onClose}>X</button>
        <div className={styles.addTeamHeader}>
          <h2>Add New Team</h2>
        </div>
        <form onSubmit={handleAddTeam} className={styles.addTeamForm}>
          <input
            className={styles.inputField}
            name="teamName"
            type="text"
            placeholder="Team Name"
            required
          />
          <div className={styles.memberSelection}>
            <label>Select Members:</label>
            <div className={styles.memberList}>
              {people.map((person, index) => (
                <div
                  key={index}
                  className={`${styles.memberItem} ${selectedMembers.includes(person) ? styles.selectedMember : ""}`}
                  onClick={() => handleMemberSelect(person)}
                >
                  {person}
                </div>
              ))}
            </div>
          </div>
          <button type="submit" className={styles.addTeamButton}>
            Add Team
          </button>
          <button type="button" className={styles.cancelButton} onClick={onClose}>
            Cancel
          </button>
        </form>
      </div>
    </div>
  );
};

export default AddTeam;
