import React, { useState } from "react";
import styles from "../styles/teams.module.css";
import TeamView from "../components/team overview/TeamView";
import AddTeam from "../components/team overview/AddTeam"; // Import the AddTeam component

// Parent Component for Team Overview
const TeamOverview = () => {
  const [teams, setTeams] = useState([
    { name: "Team Alpha", members: ["Alice", "Bob", "Charlie"] },
    { name: "Team Beta", members: ["David", "Eve", "Frank"] },
  ]);
  const [isAdmin, setIsAdmin] = useState(false);
  const [showAddTeam, setShowAddTeam] = useState(false); // Track visibility of AddTeam modal

  // Function to toggle role
  const toggleRole = () => {
    setIsAdmin((prevIsAdmin) => !prevIsAdmin);
  };

  const deleteTeam = (teamName) => {
    setTeams((prevTeams) => prevTeams.filter((team) => team.name !== teamName));
  };

  // Function to toggle the Add Team modal
  const toggleAddTeam = () => {
    setShowAddTeam((prevShowAddTeam) => !prevShowAddTeam);
  };

  // Function to add a new team
  const addNewTeam = (newTeam) => {
    setTeams((prevTeams) => [...prevTeams, newTeam]);
  };

  return (
    <div>
      <div className={styles.addTeamContainer}>
        <h1>Team Overview</h1>
        <button className={styles.newTeamButton} onClick={toggleAddTeam}>+</button>
      </div>
      <div className={styles.teamOverviewContainer}>
        <div className={styles.teamOverviewHeader}>
          <button className={styles.roleToggleButton} onClick={toggleRole}>
            {isAdmin ? "Switch to User Role" : "Switch to Admin Role"}
          </button>
        </div>
        <div className={styles.teamOverviewContent}>
          {teams.map((team, index) => (
            <TeamView key={index} team={team} isAdmin={isAdmin} onDeleteTeam={deleteTeam}/>
          ))}
        </div>
      </div>
      {/* Render AddTeam modal when showAddTeam is true */}
      {showAddTeam && <AddTeam onClose={toggleAddTeam} onAddTeam={addNewTeam} />}
    </div>
  );
};

export default TeamOverview;
