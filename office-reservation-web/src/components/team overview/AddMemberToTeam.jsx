import React, { useState, useEffect } from "react";
import styles from "../../styles/addMember.module.css";
import UserCalls from "../api calls/UserCalls";
import TeamCalls from "../api calls/TeamCalls";

const AddMemberToTeam = ({ team, show, onClose, onMemberAdded }) => {
  const [allUsers, setAllUsers] = useState([]);
  const [nonTeamMembers, setNonTeamMembers] = useState([]);
  const [selectedUser, setSelectedUser] = useState(null);

  useEffect(() => {
    const fetchAllUsers = async () => {
      try {
        const users = await UserCalls.getAllUsers();
        setAllUsers(users);

        // Filter out users already in the team
        const teamMemberIds = [
          ...team.users.map((user) => user.id),
          ...team.teamManagers.map((manager) => manager.id),
        ];
        const nonMembers = users.filter((user) => !teamMemberIds.includes(user.id));
        setNonTeamMembers(nonMembers);
      } catch (error) {
        console.error("Error fetching users:", error);
      }
    };

    if (show) fetchAllUsers();
  }, [show, team]);

  const handleAddMember = async () => {
    if (!selectedUser) return;

    try {
      // Clone current team and add the selected user to the team members
      const updatedTeam = {
        ...team,
        users: [...team.users, selectedUser],
      };

      await TeamCalls.updateTeam(team.id, updatedTeam);

      // Call the parent callback to refresh the team view
      onMemberAdded(selectedUser);

      // Close the modal
      onClose();
    } catch (error) {
      console.error("Error adding member to team:", error);
    }
  };

  if (!show) return null;

  return (
    <div className={styles.modalOverlay}>
      <div className={styles.modalContent}>
        <h3>Add Member to {team.name}</h3>
        <button className={styles.closeButton} onClick={onClose}>
          X
        </button>
        {nonTeamMembers.length === 0 ? (
          <p>No users available to add</p>
        ) : (
          <ul className={styles.userList}>
            {nonTeamMembers.map((user) => (
              <li
                key={user.id}
                className={`${styles.userItem} ${
                  selectedUser?.id === user.id ? styles.selectedUser : ""
                }`}
                onClick={() => setSelectedUser(user)}
              >
                {user.firstName} {user.lastName}
              </li>
            ))}
          </ul>
        )}
        <button
          className={styles.addMemberButton}
          disabled={!selectedUser}
          onClick={handleAddMember}
        >
          Add Selected Member
        </button>
      </div>
    </div>
  );
};

export default AddMemberToTeam;
