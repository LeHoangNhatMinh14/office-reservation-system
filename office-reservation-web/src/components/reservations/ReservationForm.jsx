import React, { useState, useEffect } from "react";
import axios from "axios";
import RoomCalls from "../api calls/RoomCalls.jsx"
import Reservationcalls from "../api calls/Reservationcalls.jsx"; // Import API function to create reservations

const ReservationForm = ({ onReservationSuccess }) => {
    const [rooms, setRooms] = useState([]);
    const [selectedRoom, setSelectedRoom] = useState("");
    const [selectedTable, setSelectedTable] = useState("");
    const [selectedDate, setSelectedDate] = useState("");
    const [selectedStartTime, setSelectedStartTime] = useState("");
    const [selectedEndTime, setSelectedEndTime] = useState("");

    // Fetch all rooms on component load
    useEffect(() => {
        RoomCalls.getAllRooms()
            .then((data) => setRooms(data))
            .catch((error) => console.error("Error fetching rooms:", error));
    }, []);

    const handleRoomChange = (roomId) => {
        const room = rooms.find((r) => r.id === parseInt(roomId));
        setSelectedRoom(room || null);
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

    return (
        <form onSubmit={handleReservationSubmit}>
            <label>
                Room:
                <select onChange={(e) => handleRoomChange(e.target.value)} defaultValue="">
                    <option value="">Select a Room</option>
                    {rooms.map((room) => (
                        <option key={room.id} value={room.id}>
                            {room.name}
                        </option>
                    ))}
                </select>
            </label>
            {selectedRoom && (
                <div className="tables-list">
                    <h3>Tables for {selectedRoom.name}:</h3>
                    <ul>
                        {selectedRoom.tables.map((table) => (
                            <li key={table.id} onClick={() => setSelectedTable(table)}>
                                Table {table.islandNumber}
                            </li>
                        ))}
                    </ul>
                </div>
            )}
            <label>
                Date:
                <input
                    type="date"
                    value={selectedDate}
                    onChange={(e) => setSelectedDate(e.target.value)}
                    required
                />
            </label>
            <label>
                Start Time:
                <input
                    type="time"
                    value={selectedStartTime}
                    onChange={(e) => setSelectedStartTime(e.target.value)}
                    required
                />
            </label>
            <label>
                End Time:
                <input
                    type="time"
                    value={selectedEndTime}
                    onChange={(e) => setSelectedEndTime(e.target.value)}
                    required
                />
            </label>
            <button type="submit">Reserve</button>
        </form>
    );
};

export default ReservationForm;