import React, {useEffect, useState} from "react";
import styles from "./RoomManagement.module.css";
import RoomOverview from "./overview/RoomOverview.jsx";
import TableReservationPage from "./TableReservationPage";
import { useNavigate } from "react-router-dom";
import RoomCalls from "../api calls/RoomCalls.jsx";
import {parseRoomsResponse} from "./parseRoomsResponse.js";

const RoomService = () => {
  const [selectedRoom, setSelectedRoom] = useState(null);
  const navigate = useNavigate();
  // Initialize the room list with at least two default rooms and associated tables
  const [rooms, setRooms] = useState([]);
  const [newRoomName, setNewRoomName] = useState("");

    useEffect(() => {
        const timeout = setTimeout(() => {
            RoomCalls.getAllRooms().then(data => setRooms(parseRoomsResponse(data)));
        }, 500);
        return () => clearTimeout(timeout);
    }, []);

  // Handles creating a new room
  const handleCreateRoom = () => {
    if (!newRoomName.trim()) {
      alert("Room name is required!");
      return;
    }
    RoomCalls.createRoom({name: newRoomName, height: 600, width: 800, capacity: 0})
        .then(() => RoomCalls.getAllRooms().then(data => setRooms(data)))
  };

  // Handles deleting a room
  const handleDeleteRoom = (id) => {
    if (window.confirm("Are you sure you want to delete this room?")) {
      RoomCalls.deleteRoom(id)
          .then(() => RoomCalls.getAllRooms().then(data => setRooms(parseRoomsResponse(data))))
    }
  };

  return (
    <div className={styles.roomServiceContainer}>
      {/* Add Room Section */}
      <div className={styles.addRoomSection}>
        <input
          type="text"
          placeholder="Room Name"
          value={newRoomName}
          onChange={(e) => setNewRoomName( e.target.value )}
          className={styles.inputField}
        />
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
                <p>Number of Tables: {room.tables.length}</p>
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
        room={selectedRoom}
        refreshRooms={() => RoomCalls.getAllRooms().then(data => setRooms(parseRoomsResponse(data)))}
      />
      <div className={styles.tableReservationHeader}>
        <h2>Table Reservation</h2>
        <button
                    className={styles.addLeaveDaysBtn}
                    onClick={() => navigate("/set-leave-days")}
                >
                    Set Leave Days
                </button>
      </div>
      <TableReservationPage rooms={rooms} />
    </div>
  );
};

export default RoomService;
