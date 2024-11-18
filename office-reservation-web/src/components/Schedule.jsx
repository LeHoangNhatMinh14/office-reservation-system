import React from "react";
import styles from "../styles/Schedule.module.css";

const Schedule = () => {
    const schedule = [
        {
            day: "Monday",
            tasks: [
                {
                    team: "Team Alpha",
                    members: 6,
                    start: "09:00",
                    end: "11:00",
                },
            ],
        },
        {
            day: "Tuesday",
            tasks: [
                {
                    team: "Team Beta",
                    members: 4,
                    start: "10:00",
                    end: "12:00",
                },
            ],
        },
        {
            day: "Wednesday",
            tasks: [
                {
                    team: "Team Gamma",
                    members: 5,
                    start: "14:00",
                    end: "16:00",
                },
            ],
        },
        {
            day: "Thursday",
            tasks: [
                {
                    team: "Team Delta",
                    members: 7,
                    start: "08:30",
                    end: "10:30",
                },
            ],
        },
        {
            day: "Friday",
            tasks: [],
        },
    ];

    return (
        <div className={styles.scheduleContainer}>
            {schedule.map((day) => (
                <div className={styles.day} key={day.day}>
                    <h2>{day.day}</h2>
                    {day.tasks.length > 0 ? (
                        day.tasks.map((task, index) => (
                            <div className={styles.work} key={index}>
                                <h3>{task.team}</h3>
                                <p>Members: {task.members}</p>
                                <p className={styles.time}>
                                    {task.start} - {task.end}
                                </p>
                            </div>
                        ))
                    ) : (
                        <p className={styles.noTasks}>No tasks scheduled</p>
                    )}
                </div>
            ))}
        </div>
    );
};

export default Schedule;
