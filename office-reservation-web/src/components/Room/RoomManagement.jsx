import React, { useState } from "react";
import "./RoomManagement.module.css";

const RoomManagement = () => {
    const [layout, setLayout] = useState(""); // Selected layout
    const [rooms, setRooms] = useState([]); // List of rooms
    const [roomName, setRoomName] = useState(""); // Room name

    // Predefined layouts
    const layouts = {
        "U-shape": (
            <div className="uShape">
                <div className="table horizontal"></div>
                <div className="table vertical left"></div>
                <div className="table vertical right"></div>
            </div>
        ),
        "Round Table": (
            <div className="roundTable">
                <div className="table circle"></div>
            </div>
        ),
        Clusters: (
            <div className="clusters">
                <div className="table cluster"></div>
                <div className="table cluster"></div>
                <div className="table cluster"></div>
            </div>
        ),
        "Classroom": (
            <div className="classroom">
                <div className="table horizontal"></div>
                <div className="table horizontal"></div>
                <div className="table horizontal"></div>
            </div>
        ),
    };

    // Save room
    const handleSaveRoom = () => {
        if (roomName && layout) {
            setRooms([...rooms, { name: roomName, layout }]);
            setRoomName("");
            setLayout("");
        }
    };

    return (
        <div className="roomManagementContainer">
            <h1>Room Management</h1>

            {/* Room and Layout Selection */}
            <div className="form">
                <input
                    type="text"
                    placeholder="Enter Room Name"
                    value={roomName}
                    onChange={(e) => setRoomName(e.target.value)}
                />
                <select
                    onChange={(e) => setLayout(e.target.value)}
                    value={layout}
                >
                    <option value="" disabled>
                        Select Layout
                    </option>
                    {Object.keys(layouts).map((key) => (
                        <option key={key} value={key}>
                            {key}
                        </option>
                    ))}
                </select>
                <button onClick={handleSaveRoom}>Save Room</button>
            </div>

            {/* Preview Layout */}
            <div className="layoutPreview">
                <h2>Preview: {layout || "No Layout Selected"}</h2>
                {layouts[layout] || <p>Select a layout to preview</p>}
            </div>

            {/* Saved Rooms */}
            <div className="savedRooms">
                <h2>Saved Rooms</h2>
                {rooms.length > 0 ? (
                    rooms.map((room, index) => (
                        <div key={index} className="roomCard">
                            <h3>{room.name}</h3>
                            <div className="layoutPreview">
                                {layouts[room.layout]}
                            </div>
                        </div>
                    ))
                ) : (
                    <p>No rooms created yet.</p>
                )}
            </div>
        </div>
    );
};

export default RoomManagement;