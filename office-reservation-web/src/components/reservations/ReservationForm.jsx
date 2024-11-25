import React, { useState, useEffect } from "react";
import RoomCalls from "../api calls/RoomCalls.jsx";
import Reservationcalls from "../api calls/Reservationcalls.jsx";
import styles from "../../styles/TableReserv.module.css";

const ReservationForm = ({ onReservationSuccess }) => {
    const [rooms, setRooms] = useState([]);
    const [selectedRoom, setSelectedRoom] = useState(null);
    const [selectedTable, setSelectedTable] = useState("");
    const [selectedDate, setSelectedDate] = useState("");
    const [selectedStartTime, setSelectedStartTime] = useState("");
    const [selectedEndTime, setSelectedEndTime] = useState("");
    const [endTimeMin, setEndTimeMin] = useState("");

    // Fetch all rooms on component load
    useEffect(() => {
        RoomCalls.getAllRooms()
            .then((data) => setRooms(data))
            .catch((error) => console.error("Error fetching rooms:", error));
    }, []);

    const handleRoomChange = (roomId) => {
        const room = rooms.find((r) => r.id === parseInt(roomId));
        setSelectedRoom(room || null);
        setSelectedTable(""); // Clear table selection when room changes
    };

    const handleReservationSubmit = (e) => {
        e.preventDefault();
        const reservationData = {
            date: selectedDate,
            startTime: selectedStartTime,
            endTime: selectedEndTime,
            table: { id: selectedTable },
            reservationUser: { id: 1 }, // Example user ID
        };

        Reservationcalls.createReservation(reservationData)
            .then((response) => {
                console.log("Reservation created successfully:", response);
                onReservationSuccess();
            })
            .catch((error) => console.error("Error creating reservation:", error));
    };

    const addMinutes = (time, minutes) => {
        const [hour, minute] = time.split(":").map(Number);
        const date = new Date();
        date.setHours(hour, minute + minutes);
        return date.toTimeString().slice(0, 5); // Return HH:MM format
    };

    const handleStartTimeChange = (startTime) => {
        setSelectedStartTime(startTime);
        setEndTimeMin(addMinutes(startTime, 30)); // Update the minimum allowed End Time
        setSelectedEndTime(""); // Clear the End Time to avoid conflicts
    };

    const getTodayDate = () => {
        const today = new Date();
        const yyyy = today.getFullYear();
        const mm = String(today.getMonth() + 1).padStart(2, "0");
        const dd = String(today.getDate()).padStart(2, "0");
        return `${yyyy}-${mm}-${dd}`;
    };

    return (
        <div className={styles.reservationContainer}>
            <form onSubmit={handleReservationSubmit}>
                <div className={styles.formGroup}>
                    <label>Room:</label>
                    <select onChange={(e) => handleRoomChange(e.target.value)} defaultValue="">
                        <option value="">Select a Room</option>
                        {rooms.map((room) => (
                            <option key={room.id} value={room.id}>
                                {room.name}
                            </option>
                        ))}
                    </select>
                </div>
                <div className={styles.formGroup}>
                    <label>Tables:</label>
                    <select
                        onChange={(e) => setSelectedTable(e.target.value)}
                        value={selectedTable}
                        disabled={!selectedRoom} // Disable if no room is selected
                    >
                        <option value="">Select a Table</option>
                        {selectedRoom &&
                            selectedRoom.tables.map((table) => (
                                <option key={table.id} value={table.id}>
                                    Table {table.islandNumber}
                                </option>
                            ))}
                    </select>
                </div>
                <div className={styles.formGroup}>
                    <label>Date:</label>
                    <input
                        type="date"
                        value={selectedDate}
                        onChange={(e) => setSelectedDate(e.target.value)}
                        required
                        min={getTodayDate()}
                    />
                </div>
                <div className={styles.formGroup}>
                    <label>Start Time:</label>
                    <input
                        type="time"
                        value={selectedStartTime}
                        onChange={(e) => handleStartTimeChange(e.target.value)}
                        required
                    />
                </div>
                <div className={styles.formGroup}>
                    <label>End Time:</label>
                    <input
                        type="time"
                        value={selectedEndTime}
                        onChange={(e) => setSelectedEndTime(e.target.value)}
                        min={endTimeMin}
                        required
                    />
                </div>
                <button type="submit">Reserve</button>
            </form>
        </div>
    );
};

export default ReservationForm;
