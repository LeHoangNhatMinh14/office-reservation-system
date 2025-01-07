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
            // Calculate the start of the week (Monday) and the week range
            const weekStart = startOfWeek(currentDate, { weekStartsOn: 1 }); // Monday as the first day
            const weekEnd = addDays(weekStart, 6); // End of the week = Sunday
    
            console.log("Week Start (Monday):", format(weekStart, "yyyy-MM-dd"));
            console.log("Week End (Sunday):", format(weekEnd, "yyyy-MM-dd"));
    
            // Fetch reservations based on the start of the week
            const reservations = await ReservationApi.getAllReservationsWeekly(format(weekStart, "yyyy-MM-dd"));
    
            console.log("Fetched Reservations:", reservations);
    
            // Create a schedule for Monday to Sunday
            const formattedSchedule = Array.from({ length: 7 }, (_, dayIndex) => {
                const day = addDays(weekStart, dayIndex); // Start from Monday
                const dayKey = format(day, "yyyy-MM-dd");
    
                // Normalize the date from the backend for comparison
                const dailyReservations = reservations.filter((reservation) => {
                    const reservationDate = format(new Date(reservation.date), "yyyy-MM-dd");
                    console.log(`Reservation Date: ${reservationDate}, Day Key: ${dayKey}`);
                    return reservationDate === dayKey;
                });
    
                console.log(`Reservations for ${dayKey}:`, dailyReservations);
    
                return {
                    day: format(day, "EEEE"),
                    reservations: dailyReservations,
                };
            });
    
            console.log("Formatted Schedule:", formattedSchedule);
            setSchedules(formattedSchedule);
        } catch (error) {
            console.error("Error fetching reservations:", error.message || error.response?.data || error);
        }
    };
    

    const handlePreviousWeek = () => {
        setCurrentWeek(currentWeek - 1);
        setCurrentDate(addDays(currentDate, -7)); // Move back 7 days
    };
    
    const handleNextWeek = () => {
        setCurrentWeek(currentWeek + 1);
        setCurrentDate(addDays(currentDate, 7)); // Move forward 7 days
    };    

    const weekDates = Array.from({ length: 7 }, (_, index) => addDays(currentDate, index));

    return (
        <div className={styles.scheduleContainer}>
            <div className={styles.headerContainer}>
            <div className={styles.headerIntersectionBox}></div>
            {Array.from({ length: 7 }, (_, index) => {
                const day = addDays(startOfWeek(currentDate, { weekStartsOn: 1 }), index); // Start on Monday
                return (
                    <div key={index} className={styles.weekDayHeader}>
                        <h2>{format(day, "EEEE")}</h2>
                        <h3>{format(day, "MMMM d, yyyy")}</h3>
                    </div>
                );
            })}
        </div>

            <div className={styles.calendarGrid}>
                <div className={styles.timeColumn}>
                    {Array.from({ length: 24 }, (_, hour) => (
                        <div key={hour} className={styles.timeSlot}>
                            {`${hour.toString().padStart(2, "0")}:00`}
                        </div>
                    ))}
                </div>
                {weekDates.map((date, index) => (
                    <div key={index} className={styles.dayColumn}>
                        <div className={styles.dayContent}>
                            {schedules[index]?.reservations.map((reservation, resIndex) => {
                                const startHour = parseInt(reservation.startTime.split(":")[0]);
                                const endHour = parseInt(reservation.endTime.split(":")[0]);
                                return (
                                    <div
                                        key={resIndex}
                                        className={styles.reservationBlock}
                                        style={{
                                            gridRow: `${startHour + 1} / span ${endHour - startHour}`,
                                        }}
                                    >
                                        <h3>
                                            {reservation.reservationType === "TEAM"
                                                ? "Team Reservation"
                                                : "Individual Reservation"}
                                        </h3>
                                        <p>Reservation ID: {reservation.id}</p>
                                        <p>Team: {reservation.teamId || "N/A"}</p>
                                        <p>Reserved by: {reservation.userId}</p>
                                        <p>Table: {reservation.tableId}</p>
                                    </div>
                                );
                            })}
                        </div>
                    </div>
                ))}
            </div>

            <div className={styles.navigationButtonsBottom}>
                <button className={styles.button} onClick={handlePreviousWeek}>
                    Previous Week
                </button>
                <span>Week {format(currentDate, "w")}</span>
                <button className={styles.button} onClick={handleNextWeek}>
                    Next Week
                </button>
            </div>
        </div>
    );
};

export default SchedulePage;
