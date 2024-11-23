import React, { useState } from "react";
import "./RoomReservation.module.css";

const RoomReservation = () => {
    const [rooms, setRooms] = useState([
        "Auditorium 1",
        "Auditorium 2",
        "Boardroom",
        "Computer Room",
        "Lecture Room 1",
        "Lecture Room 2",
        "Lecture Room 3",
    ]);

    const [days, setDays] = useState(["Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"]);

    const [reservations, setReservations] = useState({
        "Auditorium 1": ["Book", "Book", "Meeting", "Book", "Event", "Book", "Closed"],
        "Auditorium 2": ["Lecture", "Closed", "Book", "Book", "Workshop", "Book", "Book"],
        Boardroom: ["Book", "Meeting", "Book", "Book", "Book", "Closed", "Closed"],
        "Computer Room": ["Book", "Book", "Exam", "Exam", "Book", "Event", "Book"],
        "Lecture Room 1": ["Lecture", "Lecture", "Lecture", "Book", "Book", "Lecture", "Closed"],
        "Lecture Room 2": ["Workshop", "Book", "Book", "Book", "Book", "Book", "Closed"],
        "Lecture Room 3": ["Book", "Closed", "Lecture", "Workshop", "Book", "Book", "Event"],
    });

    return (
        <div className="reservation-container">
            <div className="reservation-header">
                <div className="empty-cell"></div>
                {days.map((day, index) => (
                    <div key={index} className="header-cell">
                        {day}
                    </div>
                ))}
            </div>

            {rooms.map((room, roomIndex) => (
                <div key={roomIndex} className="reservation-row">
                    <div className="room-name">{room}</div>
                    {reservations[room].map((status, index) => (
                        <div
                            key={index}
                            className={`reservation-cell ${
                                status.toLowerCase() === "book"
                                    ? "available"
                                    : status.toLowerCase() === "closed"
                                        ? "closed"
                                        : "reserved"
                            }`}
                        >
                            {status}
                        </div>
                    ))}
                </div>
            ))}
        </div>
    );
};

export default RoomReservation;
