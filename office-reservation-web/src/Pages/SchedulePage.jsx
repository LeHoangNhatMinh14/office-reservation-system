import React, { useEffect, useState } from "react";
import styles from "../styles/Schedule.module.css";
import { addDays, format, startOfWeek } from "date-fns";
import ReservationApi from "../components/api calls/Reservationcalls";

const ScheduleTable = () => {
    const [currentWeek, setCurrentWeek] = useState(0);
    const [currentDate, setCurrentDate] = useState(startOfWeek(new Date(), { weekStartsOn: 1 })); // Monday
    const [reservations, setReservations] = useState([]);

    useEffect(() => {
        fetchReservations();
    }, [currentWeek]);

    const fetchReservations = async () => {
        try {
            const weekStart = startOfWeek(currentDate, { weekStartsOn: 1 });
            const formattedStartDate = format(weekStart, "yyyy-MM-dd");

            const fetchedReservations = await ReservationApi.getAllReservationsWeekly(formattedStartDate);
            setReservations(fetchedReservations);
        } catch (error) {
            console.error("Error fetching reservations:", error);
            setReservations([]);
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

    // 30-minute time slots
    const timeSlots = Array.from({ length: 48 }, (_, index) => {
        const hour = Math.floor(index / 2).toString().padStart(2, "0");
        const minutes = index % 2 === 0 ? "00" : "30";
        return `${hour}:${minutes}`;
    });

    const daysOfWeek = Array.from({ length: 7 }, (_, index) =>
        addDays(startOfWeek(currentDate, { weekStartsOn: 1 }), index)
    );

    return (
        <div className={styles.scheduleContainer}>
            <div className={styles.tableWrapper}>
                <table className={styles.scheduleTable}>
                    <thead>
                        <tr>
                            <th className={styles.timeHeader}></th>
                            {daysOfWeek.map((day, index) => (
                                <th key={index} className={styles.tableHeader}>
                                    {format(day, "EEEE")}
                                    <br />
                                    {format(day, "MMMM d, yyyy")}
                                </th>
                            ))}
                        </tr>
                    </thead>
                    <tbody>
                        {timeSlots.map((slot, rowIndex) => (
                            <tr key={rowIndex}>
                                <td className={styles.timeCell}>{slot}</td>
                                {daysOfWeek.map((day, colIndex) => {
                                    const dayKey = format(day, "yyyy-MM-dd");

                                    const reservationForSlot = reservations.find(
                                        (res) =>
                                            res.date === dayKey &&
                                            res.startTime.slice(0, 5) === slot // Match reservation starting at this slot
                                    );

                                    return (
                                        <td key={colIndex} className={styles.tableCell}>
                                            {reservationForSlot && (
                                                <div
                                                className={styles.reservationBlock}
                                                style={{
                                                    height: `${
                                                        30 * 2 + 30 // Two rows (30px each) + small offset for the border
                                                    }px`,
                                                    top: 0, // Align to the top of the row
                                                }}
                                                >
                                                    <p><strong>Type:</strong> {reservationForSlot.reservationType}</p>
                                                    <p><strong>Time:</strong> {reservationForSlot.startTime} - {reservationForSlot.endTime}</p>
                                                </div>
                                            )}
                                        </td>
                                    );
                                })}
                            </tr>
                        ))}
                    </tbody>
                </table>
            </div>
            <div className={styles.navigationButtonsBottom}>
                <button className={styles.button} onClick={handlePreviousWeek}>
                    Previous Week
                </button>
                <span className={styles.weekLabel}>Week {format(currentDate, "w")}</span>
                <button className={styles.button} onClick={handleNextWeek}>
                    Next Week
                </button>
            </div>
        </div>
    );
};

export default ScheduleTable;
