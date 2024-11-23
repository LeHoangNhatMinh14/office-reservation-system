import React, { useState, useEffect } from "react";
import styles from "../styles/teams.module.css";
import TeamView from "../components/team overview/TeamView";
import AddTeam from "../components/team overview/AddTeam";
import TeamCalls from "../components/api calls/TeamCalls";
import Svg from "../assets/plusTeam.svg";

const TeamOverview = () => {
  const [teams, setTeams] = useState([]);
  const [isAdmin, setIsAdmin] = useState(false);
  const [showAddTeam, setShowAddTeam] = useState(false);

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

  const toggleRole = () => {
    setIsAdmin((prev) => !prev);
  };

  const toggleAddTeamModal = () => {
    setShowAddTeam((prev) => !prev);
  };

  const handleAddNewTeam = (newTeam) => {
    setTeams((prev) => [...prev, newTeam]);
  };

  const handleDeleteTeam = async (teamId) => {
    try {
      await TeamCalls.deleteTeam(teamId);
      setTeams((prev) => prev.filter((team) => team.id !== teamId));
    } catch (error) {
      console.error("Error deleting team:", error);
      alert("Failed to delete the team. Please try again.");
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
      // Fetch the team, remove the member, and update the team
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
        <div className={styles.right}>
          <button
            className={styles.newTeamButton}
            onClick={toggleAddTeamModal}
            title="Add New Team"
          >
            <img src={Svg} alt="Add Team" />
          </button>
        </div>
      </div>
      <div className={styles.teamOverviewContainer}>
        <button className={styles.roleToggleButton} onClick={toggleRole}>
          {isAdmin ? "Switch to User Role" : "Switch to Admin Role"}
        </button>
        <div className={styles.teamOverviewContent}>
          {teams.length > 0 ? (
            teams.map((team) => (
              <TeamView
                key={team.id}
                team={team}
                isAdmin={isAdmin}
                onDeleteTeam={() => handleDeleteTeam(team.id)}
                onEditTeam={(updatedData) =>
                  handleEditTeam(team.id, updatedData)
                }
                onRemoveMember={(userId) =>
                  handleRemoveTeamMember(team.id, userId)
                }
              />
            ))
          ) : (
            <p>No teams available</p>
          )}
        </div>
      </div>
      {showAddTeam && (
        <AddTeam
          onClose={toggleAddTeamModal}
          onAddTeam={handleAddNewTeam}
        />
      )}
    </div>
  );
};

export default TeamOverview;
