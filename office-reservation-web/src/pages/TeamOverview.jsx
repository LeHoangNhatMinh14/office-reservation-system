import React, { useState, useEffect } from "react";
import styles from "../styles/teams.module.css";
import TeamView from "../components/team overview/TeamView";
import AddTeam from "../components/team overview/AddTeam"; // Import the AddTeam component
import TeamCalls from "../components/api calls/TeamCalls";
import Svg from "../assets/plusTeam.svg"

// Parent Component for Team Overview
const TeamOverview = () => {
  const [teams, setTeams] = useState([]);
  const [isAdmin, setIsAdmin] = useState(false);
  const [showAddTeam, setShowAddTeam] = useState(false); // Track visibility of AddTeam modal

  useEffect(() => {
    // Fetch teams from API when the component mounts
    const fetchTeams = async () => {
      try {
        const fetchedTeams = await TeamCalls.getAllTeams();
        // Ensure fetchedTeams is an array before setting state
        setTeams(Array.isArray(fetchedTeams) ? fetchedTeams : []);
      } catch (error) {
        console.error("Error fetching teams:", error);
      }
    };

    fetchTeams();
  }, []);

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
        <div className={styles.right}>
          <button className={styles.newTeamButton} onClick={toggleAddTeam}>
            <img src={Svg} alt="" />
          </button>
        </div>
      </div>
      <div className={styles.teamOverviewContainer}>
        <div className={styles.teamOverviewHeader}>
          <button className={styles.roleToggleButton} onClick={toggleRole}>
            {isAdmin ? "Switch to User Role" : "Switch to Admin Role"}
          </button>
        </div>
        <div className={styles.teamOverviewContent}>
          {teams && teams.length > 0 ? (
            teams.map((team, index) => (
              <TeamView key={index} team={team} isAdmin={isAdmin} onDeleteTeam={deleteTeam} />
            ))
          ) : (
            <p>No teams available</p>
          )}
        </div>
      </div>
      {/* Render AddTeam modal when showAddTeam is true */}
      {showAddTeam && <AddTeam onClose={toggleAddTeam} onAddTeam={addNewTeam} />}
    </div>
  );
};

export default TeamOverview;
