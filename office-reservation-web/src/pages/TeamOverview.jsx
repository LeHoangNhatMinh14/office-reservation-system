import React, { useState } from "react";
import styles from "../styles/teams.module.css";
import TeamView from "../components/team overview/TeamView";


// Parent Component for Team Overview
const TeamOverview = () => {
  const [teams, setTeams] = useState([{
    name: "Team Alpha",
    members: ["Alice", "Bob", "Charlie"],
  }, {
    name: "Team Beta",
    members: ["David", "Eve", "Frank"],
  }]);
  const [isAdmin, setIsAdmin] = useState(false);

  const toggleRole = () => {
    setIsAdmin((prevIsAdmin) => !prevIsAdmin);
  };

  return (
      <div className={styles.teamOverviewContainer}>
        <div className={styles.teamOverviewHeader}>
          <h1>Team Overview</h1>
          <button className={styles.roleToggleButton} onClick={toggleRole}>{isAdmin ? "Switch to User Role" : "Switch to Admin Role"}</button>
        </div>
        <div className={styles.teamOverviewContent}>
          {teams.map((team, index) => (
            <TeamView key={index} team={team} isAdmin={isAdmin} />
          ))}
        </div>
      </div>
  );
};

export default TeamOverview;