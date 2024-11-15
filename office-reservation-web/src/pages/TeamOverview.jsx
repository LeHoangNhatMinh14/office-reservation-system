import React, { useState } from "react";
import styles from "../styles/teams.module.css";
import TeamView from "../components/team overview/TeamView";
import RoleSelector from "../components/team overview/RoleSelector";

// Parent Component for Team Overview
const TeamOverview = () => {
  const [teams, setTeams] = useState([{
    name: "Team Alpha",
    members: ["Alice", "Bob", "Charlie"],
  }, {
    name: "Team Beta",
    members: ["David", "Eve", "Frank"],
  }]);

  return (
    <div className={styles.teamOverviewContainer}>
      <div className={styles.teamOverviewHeader}>
        <h1>Team Overview</h1>
      </div>
      <div className={styles.teamOverviewContent}>
        {teams.map((team, index) => (
          <TeamView key={index} team={team} />
        ))}
      </div>
    </div>
  );
};

export default TeamOverview;
