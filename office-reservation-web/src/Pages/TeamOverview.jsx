import React, { useState, useEffect } from "react";
import styles from "../styles/teams.module.css";
import TeamView from "../components/team overview/TeamView";
import AddTeam from "../components/team overview/AddTeam";
import TeamCalls from "../components/api calls/TeamCalls";
import Svg from "../assets/plusTeam.svg";
import TokenManager from "../components/api calls/TokenManager";

const TeamOverview = () => {
  const [teams, setTeams] = useState([]); // List of all teams
  const [isAdmin, setIsAdmin] = useState(false); // Admin mode determined by token role
  const [showAddTeam, setShowAddTeam] = useState(false); // Modal visibility
  const [errorMessage, setErrorMessage] = useState(""); // Error message for failures

  // Fetch user role from the token
  useEffect(() => {
    const role = TokenManager.getUserRole();
    console.log("User role:", role); // Should log "ADMIN" or the correct role
    setIsAdmin(role === "ADMIN");
  }, []);

  // Fetch all teams on component mount
  useEffect(() => {
    const fetchTeams = async () => {
      try {
        const fetchedTeams = await TeamCalls.getAllTeams();
        setTeams(Array.isArray(fetchedTeams) ? fetchedTeams : []);
      } catch (error) {
        console.error("Error fetching teams:", error);
        alert("Failed to load teams. Please try again.");
      }
    };

    fetchTeams();
  }, []);

  const toggleAddTeamModal = () => {
    setShowAddTeam((prev) => !prev);
  };

  const handleAddNewTeam = (newTeam) => {
    if (!newTeam) {
      setErrorMessage("Failed to add team. Please try again.");
      return;
    }
    setTeams((prev) => [...prev, newTeam]);
  };

  const handleDeleteTeam = async (teamId) => {
    try {
      console.log("Attempting to delete team with ID:", teamId);
      await TeamCalls.deleteTeam(teamId);
      setTeams((prevTeams) => prevTeams.filter((team) => team.id !== teamId)); // Dynamically update state
      console.log("Team deleted and UI updated");
    } catch (error) {
      if (error.response?.status === 404) {
        console.warn("Team not found (404). Removing from UI.");
        setTeams((prevTeams) => prevTeams.filter((team) => team.id !== teamId)); // Handle case gracefully
      } else {
        console.error("Error deleting team:", error);
        alert("Failed to delete the team. Please try again.");
      }
    }
  };
  
  
  const handleEditTeam = async (teamId, updatedData) => {
    try {
      const updatedTeam = await TeamCalls.updateTeam(teamId, updatedData);
      setTeams((prev) =>
        prev.map((team) => (team.id === teamId ? updatedTeam : team))
      );
    } catch (error) {
      console.error("Error updating team:", error);
      alert("Failed to update the team. Please try again.");
    }
  };

  const handleRemoveTeamMember = async (teamId, userId) => {
    try {
      const team = await TeamCalls.getTeamById(teamId);
      const updatedTeam = {
        ...team,
        users: team.users.filter((user) => user.id !== userId),
      };
      await TeamCalls.updateTeam(teamId, updatedTeam);
      setTeams((prev) =>
        prev.map((team) => (team.id === teamId ? updatedTeam : team))
      );
    } catch (error) {
      console.error("Error removing team member:", error);
      alert("Failed to remove team member. Please try again.");
    }
  };

  return (
    <div>
      <div className={styles.addTeamContainer}>
        <h1>Team Overview</h1>
        {isAdmin && (
          <div className={styles.right}>
            <button
              className={styles.newTeamButton}
              onClick={toggleAddTeamModal}
              title="Add New Team"
            >
              <img src={Svg} alt="Add Team" />
            </button>
          </div>
        )}
      </div>
      <div className={styles.teamOverviewContainer}>
        <div className={styles.teamOverviewContent}>
          {teams.length > 0 ? (
            teams.map((team) => (
            <TeamView
              key={team.id}
              team={team}
              isAdmin={isAdmin}
              onDeleteTeam={() => handleDeleteTeam(team.id)}
              onEditTeam={(updatedData) => handleEditTeam(team.id, updatedData)}
              onRemoveMember={(userId) => handleRemoveTeamMember(team.id, userId)}
              onMemberAdded={(newMember) => {
                // Update the global teams state
                setTeams((prev) =>
                  prev.map((t) =>
                    t.id === team.id
                      ? {
                          ...t,
                          users: [...t.users, newMember],
                        }
                      : t
                  )
                );
              }}
            />
            ))
          ) : (
            <p>No teams available</p>
          )}
        </div>
      </div>
      {showAddTeam && (
        <AddTeam onClose={toggleAddTeamModal} onAddTeam={handleAddNewTeam} />
      )}
      {errorMessage && <p className={styles.error}>{errorMessage}</p>}
    </div>
  );
};

export default TeamOverview;
