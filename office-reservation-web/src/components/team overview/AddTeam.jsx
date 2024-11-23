import React, { useState, useEffect } from "react";
import styles from "../../styles/teamsAdd.module.css";
import TeamApi from "../api calls/TeamCalls";
import UserApi from "../api calls/UserCalls";

const AddTeam = ({ onClose, onAddTeam }) => {
  const [people, setPeople] = useState([]); // Dynamically fetched users
  const [selectedMembers, setSelectedMembers] = useState([]);

  // Fetch all users when the component mounts
  useEffect(() => {
    const fetchUsers = async () => {
      try {
        const users = await UserApi.getAllUsers();
        const formattedUsers = users.map(({ id, firstName, lastName }) => ({
          id,
          name: `${firstName} ${lastName}`,
        }));
        setPeople(formattedUsers);
      } catch (error) {
        console.error("Failed to fetch users:", error);
        alert("An error occurred while fetching users. Please try again.");
      }
    };

    fetchUsers();
  }, []);

  const handleAddTeam = async (event) => {
    event.preventDefault();

    const teamName = event.target.teamName.value.trim();
    if (!teamName) {
      alert("Please enter a valid team name.");
      return;
    }

    const newTeam = {
      name: teamName,
      users: selectedMembers,
      teamManagers: [], // Add manager selection logic here if needed
    };

    try {
      const createdTeam = await TeamApi.createTeam(newTeam);
      if (onAddTeam) onAddTeam(createdTeam); // Update parent component
      onClose(); // Close the modal
    } catch (error) {
      console.error("Failed to add team:", error);
      alert("An error occurred while adding the team. Please try again.");
    }
  };

  const toggleMemberSelection = (member) => {
    setSelectedMembers((prev) =>
      prev.some((m) => m.id === member.id)
        ? prev.filter((m) => m.id !== member.id)
        : [...prev, member]
    );
  };

  return (
    <div className={styles.modalBackdrop}>
      <div className={styles.addTeamModal}>
        <button className={styles.closeButton} onClick={onClose}>
          X
        </button>
        <h2 className={styles.addTeamHeader}>Add New Team</h2>
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
              {people.length > 0 ? (
                people.map((person) => (
                  <div
                    key={person.id}
                    className={`${styles.memberItem} ${
                      selectedMembers.some((m) => m.id === person.id)
                        ? styles.selectedMember
                        : ""
                    }`}
                    onClick={() => toggleMemberSelection(person)}
                  >
                    {person.name}
                  </div>
                ))
              ) : (
                <p>Loading members...</p>
              )}
            </div>
          </div>
          <div className={styles.buttonGroup}>
            <button type="submit" className={styles.addTeamButton}>
              Add Team
            </button>
            <button
              type="button"
              className={styles.cancelButton}
              onClick={onClose}
            >
              Cancel
            </button>
          </div>
        </form>
      </div>
    </div>
  );
};

export default AddTeam;
