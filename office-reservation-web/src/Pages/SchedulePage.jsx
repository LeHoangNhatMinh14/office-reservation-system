import React, { useState } from "react";
import styles from "../styles/Schedule.module.css";
import { addDays, format, startOfWeek } from "date-fns";

const SchedulePage = () => {
    const schedules = [
        [
            {
                day: "Sunday",
                reservations: [
                    {
                        date: "2024-12-22",
                        startTime: "09:00",
                        endTime: "11:00",
                        tableId: 1,
                        reservationType: "Team Meeting",
                        teamName: "Team Alpha",
                        userName: "Alice Johnson",
                    },
                ],
            },
            {
                day: "Monday",
                reservations: [
                    {
                        date: "2024-12-23",
                        startTime: "10:00",
                        endTime: "12:00",
                        tableId: 2,
                        reservationType: "Project Discussion",
                        teamName: "Team Beta",
                        userName: "Bob Smith",
                    },
                ],
            },
            {
                day: "Tuesday",
                reservations: [
                    {
                        date: "2024-12-24",
                        startTime: "13:00",
                        endTime: "15:00",
                        tableId: 3,
                        reservationType: "Team Sync",
                        teamName: "Team Gamma",
                        userName: "Charlie Brown",
                    },
                ],
            },
            {
                day: "Wednesday",
                reservations: [
                    {
                        date: "2024-12-25",
                        startTime: "08:30",
                        endTime: "10:30",
                        tableId: 4,
                        reservationType: "Team Standup",
                        teamName: "Team Delta",
                        userName: "Dana White",
                    },
                ],
            },
            {
                day: "Thursday",
                reservations: [
                    {
                        date: "2024-12-26",
                        startTime: "14:00",
                        endTime: "16:00",
                        tableId: 5,
                        reservationType: "Team Briefing",
                        teamName: "Team Epsilon",
                        userName: "Eve Adams",
                    },
                ],
            },
            {
                day: "Friday",
                reservations: [],
            },
            {
                day: "Saturday",
                reservations: [],
            },
        ],
    ];

    const [currentWeek, setCurrentWeek] = useState(0);
    const [currentDate, setCurrentDate] = useState(startOfWeek(new Date()));

    const handlePreviousWeek = () => {
        setCurrentWeek(currentWeek - 1);
        setCurrentDate(addDays(currentDate, -7));
    };

    const handleNextWeek = () => {
        setCurrentWeek(currentWeek + 1);
        setCurrentDate(addDays(currentDate, 7));
    };

    const weekDates = Array.from({ length: 7 }, (_, index) => addDays(currentDate, index));
    const currentSchedule = schedules[currentWeek] || [];

    return (
        <div className={styles.scheduleContainer}>
            {/* Fixed Header for Day and Date */}
            <div className={styles.headerContainer}>
                <div className={styles.headerIntersectionBox}></div>
                {weekDates.map((date, index) => (
                    <div key={index} className={styles.weekDayHeader}>
                        <h2>{format(date, "EEEE")}</h2>
                        <h3>{format(date, "MMMM d, yyyy")}</h3>
                    </div>
                ))}
            </div>

            {/* Scrollable Calendar Grid */}
            <div className={styles.calendarGrid}>
                <div className={styles.timeColumn}>
                    {Array.from({ length: 24 }, (_, hour) => (
                        <div key={hour} className={styles.timeSlot}>
                            {`${hour.toString().padStart(2, '0')}:00`}
                        </div>
                    ))}
                </div>
                {weekDates.map((date, index) => (
                    <div key={index} className={styles.dayColumn}>
                        <div className={styles.dayContent}>
                            {currentSchedule[index] && currentSchedule[index].reservations.map((reservation, resIndex) => (
                                <div
                                    key={resIndex}
                                    className={styles.reservationBlock}
                                    style={{
                                        gridRow: `${parseInt(reservation.startTime.split(":")[0]) + 1} / span ${parseInt(reservation.endTime.split(":")[0]) - parseInt(reservation.startTime.split(":")[0])}`,
                                    }}
                                >
                                    <h3>{reservation.reservationType}</h3>
                                    <p>Team: {reservation.teamName}</p>
                                    <p>Reserved by: {reservation.userName}</p>
                                    <p>Table: {reservation.tableId}</p>
                                </div>
                            ))}
                        </div>
                    </div>
                ))}
            </div>

            {/* Navigation Buttons */}
            <div className={styles.navigationButtonsBottom}>
                <button className={styles.button} onClick={handlePreviousWeek}>Previous Week</button>
                <span>Week {format(currentDate, 'w')}</span>
                <button className={styles.button} onClick={handleNextWeek}>Next Week</button>
            </div>
        </div>
    );
};

export default SchedulePage;
