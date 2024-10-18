import styles from "../styles/Schedule.module.css";
import React from "react";

const Schedule = () =>{


    return(
        <div className={styles.scheduleContainer}>
            <div className={styles.monday}>
                <h2>Monday</h2>
                <div className={styles.work}>
                    <h3 className={styles.team}>Team 1</h3>
                    <h2 className={styles.members}>Members: 6</h2>
                    <h2 className={styles.timeStart}>Start: 09:00</h2>
                    <h2 className={styles.timeEnd}>End: 09:00</h2>
                </div>
            </div>
            <div className={styles.tuesday}>
                <h2>Tuesday</h2>
            </div>
            <div className={styles.wednesday}>
                <h2>Wednesday</h2>
            </div>
            <div className={styles.thursday}>
                <h2>Thursday</h2>
            </div>
            <div className={styles.friday}>
                <h2>Friday</h2>
            </div>
            <div className={styles.saturday}>
                <h2>Saturday</h2>
            </div>
            <div className={styles.sunday}>
                <h2>Sunday</h2>
            </div>
        </div>
    );
}

export default Schedule;