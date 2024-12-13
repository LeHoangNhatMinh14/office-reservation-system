import React, { useState } from "react";
import styles from "./TableReservationPage.module.css";

const TableReservationPage = ({ rooms = [] }) => {
    const [selectedRoom, setSelectedRoom] = useState("");
    const [selectedTable, setSelectedTable] = useState("");
    const [formData, setFormData] = useState({
        date: "",
        startTime: "",
        endTime: "",
        reservedBy: "",
    });

    // Example reservations data
    const [reservations, setReservations] = useState([
        {
            tableId: 1,
            date: "2024-12-04",
            startTime: "10:00",
            endTime: "12:00",
        },
        {
            tableId: 1,
            date: "2024-12-04",
            startTime: "14:00",
            endTime: "15:00",
        },
    ]);

    const handleRoomChange = (e) => {
        setSelectedRoom(e.target.value);
        setSelectedTable(""); // Reset table selection
        setFormData({ ...formData, startTime: "", endTime: "" });
    };

    const handleTableChange = (e) => {
        setSelectedTable(e.target.value);
        setFormData({ ...formData, startTime: "", endTime: "" });
    };

    const handleDateChange = (e) => {
        setFormData({ ...formData, date: e.target.value });
    };

    const handleTimeChange = (e) => {
        setFormData({ ...formData, [e.target.name]: e.target.value });
    };

    const handleSubmit = (e) => {
        e.preventDefault();

        // Add the new reservation
        setReservations([
            ...reservations,
            {
                tableId: parseInt(selectedTable),
                date: formData.date,
                startTime: formData.startTime,
                endTime: formData.endTime,
            },
        ]);

        alert(`Reserved Table ${selectedTable} in Room ${selectedRoom}`);
    };

    // Get available tables for the selected room
    const availableTables =
        rooms?.find((room) => room.id === parseInt(selectedRoom))?.tables || [];

    // Filter reservations for the selected table and date
    const reservedTimes = reservations.filter(
        (res) =>
            res.tableId === parseInt(selectedTable) && res.date === formData.date
    );

    // Generate time slots
    const timeSlots = [];
    for (let hour = 8; hour < 18; hour++) {
        const slotStart = `${hour.toString().padStart(2, "0")}:00`;
        const slotEnd = `${(hour + 1).toString().padStart(2, "0")}:00`;

        // Check if slot overlaps with existing reservations
        const isReserved = reservedTimes.some(
            (res) =>
                (slotStart >= res.startTime && slotStart < res.endTime) ||
                (slotEnd > res.startTime && slotEnd <= res.endTime) ||
                (slotStart <= res.startTime && slotEnd >= res.endTime)
        );

        if (!isReserved) {
            timeSlots.push({ start: slotStart, end: slotEnd });
        }
    }

    return (
        <div className={styles.container}>
            <h1 className={styles.heading}>Table Reservation</h1>
            <form className={styles.form} onSubmit={handleSubmit}>
                <label className={styles.label}>
                    Room:
                    <select
                        name="room"
                        value={selectedRoom}
                        onChange={handleRoomChange}
                        className={styles.select}
                        required
                    >
                        <option value="">Select a Room</option>
                        {rooms.map((room) => (
                            <option key={room.id} value={room.id}>
                                {room.name}
                            </option>
                        ))}
                    </select>
                </label>
                <label className={styles.label}>
                    Table:
                    <select
                        name="table"
                        value={selectedTable}
                        onChange={handleTableChange}
                        className={styles.select}
                        required
                        disabled={!selectedRoom}
                    >
                        <option value="">Select a Table</option>
                        {availableTables.map((table) => (
                            <option key={table} value={table}>
                                Table {table}
                            </option>
                        ))}
                    </select>
                </label>
                <label className={styles.label}>
                    Date:
                    <input
                        type="date"
                        name="date"
                        value={formData.date}
                        onChange={handleDateChange}
                        className={styles.input}
                        required
                    />
                </label>
                <label className={styles.label}>
                    Start Time:
                    <select
                        name="startTime"
                        value={formData.startTime}
                        onChange={handleTimeChange}
                        className={styles.select}
                        required
                        disabled={!formData.date || !selectedTable}
                    >
                        <option value="">Select Start Time</option>
                        {timeSlots.map((slot) => (
                            <option key={slot.start} value={slot.start}>
                                {slot.start}
                            </option>
                        ))}
                    </select>
                </label>
                <label className={styles.label}>
                    End Time:
                    <select
                        name="endTime"
                        value={formData.endTime}
                        onChange={handleTimeChange}
                        className={styles.select}
                        required
                        disabled={!formData.startTime}
                    >
                        <option value="">Select End Time</option>
                        {timeSlots
                            .filter((slot) => slot.start > formData.startTime)
                            .map((slot) => (
                                <option key={slot.end} value={slot.end}>
                                    {slot.end}
                                </option>
                            ))}
                    </select>
                </label>
                <label className={styles.label}>
                    Reserved By:
                    <input
                        type="text"
                        name="reservedBy"
                        value={formData.reservedBy}
                        onChange={(e) =>
                            setFormData({ ...formData, reservedBy: e.target.value })
                        }
                        className={styles.input}
                        required
                    />
                </label>
                <button type="submit" className={styles.button}>
                    Reserve Table
                </button>
            </form>
        </div>
    );
};

export default TableReservationPage;
