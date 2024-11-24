import React, { useState, useEffect } from "react";
import RoomApi from "./RoomCalls.jsx";
import "../RoomManagement.css";


const RoomService = () => {
    const [rooms, setRooms] = useState([]);
    const [newRoomName, setNewRoomName] = useState("");

    useEffect(() => {
        fetchRooms();
    }, []);

    const fetchRooms = async () => {
        try {
            const fetchedRooms = await RoomApi.getAllRooms();
            setRooms(fetchedRooms);
        } catch (error) {
            console.error("Error fetching rooms:", error);
        }
    };

    const handleCreateRoom = async () => {
        if (!newRoomName.trim()) {
            return alert("Room name cannot be empty");
        }
        try {
            const roomData = { name: newRoomName };
            await RoomApi.createRoom(roomData);
            setNewRoomName("");
            fetchRooms();
        } catch (error) {
            console.error("Error creating room:", error);
        }
    };

    const handleDeleteRoom = async (id) => {
        try {
            await RoomApi.deleteRoom(id);
            fetchRooms();
        } catch (error) {
            console.error("Error deleting room:", error);
        }
    };

    return (
        <div className={styles.roomServiceContainer}>
            <div className={styles.roomServiceHeader}>
                <div className={styles.headerCell}>Room Name</div>
                <div className={styles.headerCell}>Actions</div>
            </div>

            <div className={styles.roomGrid}>
                {rooms.map((room) => (
                    <div key={room.id} className={styles.roomCard}>
                        <div className={styles.roomCardHeader}>{room.name}</div>
                        <div className={styles.roomCardFooter}>
                            <button className={styles.btnEdit}>Edit</button>
                            <button
                                className={styles.btnDelete}
                                onClick={() => handleDeleteRoom(room.id)}
                            >
                                Delete
                            </button>
                        </div>
                    </div>
                ))}
            </div>

            <div className={styles.addRoomSection}>
                <input
                    type="text"
                    placeholder="New Room Name"
                    value={newRoomName}
                    onChange={(e) => setNewRoomName(e.target.value)}
                    className={styles.inputField}
                />
                <button
                    className={styles.addRoomBtn}
                    onClick={handleCreateRoom}
                >
                    Add Room
                </button>
            </div>
        </div>
    );
};

export default RoomService;
