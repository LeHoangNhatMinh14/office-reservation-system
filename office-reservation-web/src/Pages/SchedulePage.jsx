import React, { useEffect, useState } from "react";
import styles from "../styles/Schedule.module.css";
import { addDays, format, startOfWeek } from "date-fns";
import ReservationApi from "../components/api calls/Reservationcalls";

const SchedulePage = () => {
    const [currentWeek, setCurrentWeek] = useState(0);
    const [currentDate, setCurrentDate] = useState(startOfWeek(new Date()));
    const [schedules, setSchedules] = useState([]);

    useEffect(() => {
        fetchReservations();
    }, [currentWeek]);

    const fetchReservations = async () => {
        try {
            const weekStart = format(currentDate, "yyyy-MM-dd");
    
            // Fetch all reservations for the week starting from `weekStart`
            const reservations = await ReservationApi.getAllReservationsWeekly(weekStart);
    
            // Format reservations into a weekly schedule
            const formattedSchedule = Array.from({ length: 7 }, (_, dayIndex) => {
                const day = addDays(currentDate, dayIndex);
                const dayKey = format(day, "yyyy-MM-dd");
                return {
                    day: format(day, "EEEE"),
                    reservations: reservations.filter(reservation => reservation.date === dayKey),
                };
            });
    
            setSchedules(formattedSchedule);
        } catch (error) {
            console.error("Error fetching reservations:", error);
        }
    };    

    const handlePreviousWeek = () => {
        setCurrentWeek(currentWeek - 1);
        setCurrentDate(addDays(currentDate, -7));
    };

    const handleNextWeek = () => {
        setCurrentWeek(currentWeek + 1);
        setCurrentDate(addDays(currentDate, 7));
    };

    const weekDates = Array.from({ length: 7 }, (_, index) => addDays(currentDate, index));

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
                            {schedules[index]?.reservations.map((reservation, resIndex) => (
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
