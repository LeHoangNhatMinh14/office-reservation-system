import React, { useState } from "react";
import styles from "./RoomManagement.module.css";

const RoomService = () => {
    // Initialize the room list with at least two default rooms
    const [rooms, setRooms] = useState([
        { id: 1, name: "Conference Room A", capacity: "20", status: "Available" },
        { id: 2, name: "Meeting Room B", capacity: "15", status: "Booked" },
    ]);

    const [newRoom, setNewRoom] = useState({ name: "", capacity: "", status: "Available" });

    // Handles creating a new room
    const handleCreateRoom = () => {
        if (!newRoom.name.trim() || !newRoom.capacity.trim()) {
            alert("Room name and capacity are required!");
            return;
        }

        const roomData = { ...newRoom, id: Date.now() };
        setRooms((prevRooms) => [...prevRooms, roomData]);
        setNewRoom({ name: "", capacity: "", status: "Available" });
    };

    // Handles deleting a room
    const handleDeleteRoom = (id) => {
        if (window.confirm("Are you sure you want to delete this room?")) {
            setRooms((prevRooms) => prevRooms.filter((room) => room.id !== id));
        }
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
                                <p>Status: <span className={room.status.toLowerCase()}>{room.status}</span></p>
                            </div>
                            <div className={styles.roomActions}>
                                <button className={styles.btnDelete} onClick={() => handleDeleteRoom(room.id)}>
                                    Delete
                                </button>
                            </div>
                        </div>
                    ))
                ) : (
                    <div className={styles.noRoomsMessage}>No rooms available. Add a new room to get started!</div>
                )}
            </div>
        </div>
    );
};

export default RoomService;
