import React, { useState, useEffect } from "react";
import styles from "../../styles/teamsAdd.module.css";
import TeamApi from "../api calls/TeamCalls";
import UserApi from "../api calls/UserCalls";
import TokenManager from "../api calls/TokenManager";

const AddTeam = ({ onClose, onAddTeam }) => {
  const [people, setPeople] = useState([]);
  const [selectedMembers, setSelectedMembers] = useState([]);
  const [selectedManagers, setSelectedManagers] = useState([]);
  const [errorMessage, setErrorMessage] = useState("");

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
        if (error.response?.status === 401) {
          alert("Session expired. Please log in again.");
          TokenManager.clear();
          window.location.href = "/login";
        } else {
          console.error("Failed to fetch users:", error);
          alert("An error occurred while fetching users. Please try again.");
        }
      }
    };

    fetchUsers();
  }, []);

  const handleAddTeam = async (event) => {
    event.preventDefault();

    const teamName = event.target.teamName.value.trim();
    if (!teamName) {
      setErrorMessage("Please enter a valid team name.");
      return;
    }

    if (selectedManagers.length === 0) {
      setErrorMessage("Please assign at least one team manager.");
      return;
    }

    const newTeam = {
      name: teamName,
      users: selectedMembers,
      teamManagers: selectedManagers,
    };

    try {
      const createdTeam = await TeamApi.createTeam(newTeam);

      const fetchedTeam = await TeamApi.getTeamById(createdTeam.id);

      onAddTeam(fetchedTeam);
      onClose();
    } catch (error) {
      if (error.response?.status === 401) {
        alert("Session expired. Please log in again.");
        TokenManager.clear();
        window.location.href = "/login";
      } else if (error.response?.data?.message?.includes("NameAlreadyExistsException")) {
        setErrorMessage("A team with this name already exists.");
      } else {
        console.error("Failed to add team:", error);
        alert("An error occurred while adding the team. Please try again.");
      }
    }
  };

  const toggleSelection = (list, setList, person) => {
    setList((prev) =>
      prev.some((p) => p.id === person.id)
        ? prev.filter((p) => p.id !== person.id)
        : [...prev, person]
    );
  };

  const isDisabledForManagers = (person) =>
    selectedMembers.some((m) => m.id === person.id);

  const isDisabledForMembers = (person) =>
    selectedManagers.some((m) => m.id === person.id);

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
          {errorMessage && <p className={styles.error}>{errorMessage}</p>}

          <div className={styles.memberSelection}>
            <label>Select Team Members:</label>
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
                    style={{
                      pointerEvents: isDisabledForMembers(person) ? "none" : "auto",
                      opacity: isDisabledForMembers(person) ? 0.5 : 1,
                    }}
                    onClick={() =>
                      !isDisabledForMembers(person) &&
                      toggleSelection(selectedMembers, setSelectedMembers, person)
                    }
                  >
                    {person.name}
                  </div>
                ))
              ) : (
                <p>Loading members...</p>
              )}
            </div>
          </div>

          <div className={styles.managerSelection}>
            <label>Select Team Managers:</label>
            <div className={styles.memberList}>
              {people.length > 0 ? (
                people.map((person) => (
                  <div
                    key={person.id}
                    className={`${styles.managerItem} ${
                      selectedManagers.some((m) => m.id === person.id)
                        ? styles.selectedManager
                        : ""
                    }`}
                    style={{
                      pointerEvents: isDisabledForManagers(person) ? "none" : "auto",
                      opacity: isDisabledForManagers(person) ? 0.5 : 1,
                    }}
                    onClick={() =>
                      !isDisabledForManagers(person) &&
                      toggleSelection(selectedManagers, setSelectedManagers, person)
                    }
                  >
                    {person.name}
                  </div>
                ))
              ) : (
                <p>Loading managers...</p>
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
