import React, { useState } from "react";
import styles from "./RoomManagement.module.css";
import RoomOverview from "./overview/RoomOverview.jsx";
import TableReservationPage from "./TableReservationPage";

const RoomService = () => {
    const [selectedRoom, setSelectedRoom] = useState(null);

    // Initialize the room list with at least two default rooms and associated tables
    const [rooms, setRooms] = useState([
        { id: 1, name: "Conference Room A", capacity: "20", status: "Available", tables: [1, 2, 3] },
        { id: 2, name: "Meeting Room B", capacity: "15", status: "Booked", tables: [4, 5] },
    ]);

    const [newRoom, setNewRoom] = useState({ name: "", capacity: "", status: "Available", tables: [] });

    // Handles creating a new room
    const handleCreateRoom = () => {
        if (!newRoom.name.trim() || !newRoom.capacity.trim()) {
            alert("Room name and capacity are required!");
            return;
        }

        const roomData = { ...newRoom, id: Date.now(), tables: [] }; // Add empty tables array
        setRooms((prevRooms) => [...prevRooms, roomData]);
        setNewRoom({ name: "", capacity: "", status: "Available", tables: [] });
    };

    // Handles deleting a room
    const handleDeleteRoom = (id) => {
        if (window.confirm("Are you sure you want to delete this room?")) {
            setRooms((prevRooms) => prevRooms.filter((room) => room.id !== id));
        }
    };

    // Handles adding a table to a room
    const handleAddTableToRoom = (roomId) => {
        const tableNumber = Math.max(
            ...rooms.find((room) => room.id === roomId).tables,
            0
        ) + 1;

        setRooms((prevRooms) =>
            prevRooms.map((room) =>
                room.id === roomId
                    ? { ...room, tables: [...room.tables, tableNumber] }
                    : room
            )
        );
    };

    return (
        <div className={styles.roomServiceContainer}>
            {/* Add Room Section */}
            <div className={styles.addRoomSection}>
                <input
                    type="text"
                    placeholder="Room Name"
                    value={newRoom.name}
                    onChange={(e) => setNewRoom({ ...newRoom, name: e.target.value })}
                    className={styles.inputField}
                />
                <input
                    type="number"
                    placeholder="Capacity"
                    value={newRoom.capacity}
                    onChange={(e) => setNewRoom({ ...newRoom, capacity: e.target.value })}
                    className={styles.inputField}
                />
                <select
                    value={newRoom.status}
                    onChange={(e) => setNewRoom({ ...newRoom, status: e.target.value })}
                    className={styles.inputField}
                >
                    <option value="Available">Available</option>
                    <option value="Booked">Booked</option>
                </select>
                <button className={styles.addRoomBtn} onClick={handleCreateRoom}>
                    Add Room
                </button>
            </div>

            {/* Room List */}
            <div className={styles.roomGrid}>
                {rooms.length > 0 ? (
                    rooms.map((room) => (
                        <div key={room.id} className={styles.roomCard}>
                            <div className={styles.roomDetails}>
                                <h3>{room.name}</h3>
                                <p>Capacity: {room.capacity}</p>
                                <p>
                                    Status:{" "}
                                    <span className={room.status.toLowerCase()}>
                                        {room.status}
                                    </span>
                                </p>
                                <p>Tables: {room.tables.join(", ")}</p>
                            </div>
                            <div className={styles.roomActions}>
                                <button
                                    className={styles.btnOverview}
                                    onClick={() => setSelectedRoom(room)}
                                >
                                    Overview
                                </button>
                                <button
                                    className={styles.btnDelete}
                                    onClick={() => handleDeleteRoom(room.id)}
                                >
                                    Delete
                                </button>
                                <button
                                    className={styles.btnAddTable}
                                    onClick={() => handleAddTableToRoom(room.id)}
                                >
                                    Add Table
                                </button>
                            </div>
                        </div>
                    ))
                ) : (
                    <div className={styles.noRoomsMessage}>
                        No rooms available. Add a new room to get started!
                    </div>
                )}
            </div>
            <RoomOverview
                isOpen={!!selectedRoom}
                onClose={() => setSelectedRoom(null)}
                capacity={selectedRoom?.capacity ?? 0}
            />

            {/* Pass room data to TableReservationPage */}
            <TableReservationPage rooms={rooms} />
        </div>
    );
};

export default RoomService;
