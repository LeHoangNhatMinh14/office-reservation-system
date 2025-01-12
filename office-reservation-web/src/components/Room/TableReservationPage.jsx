import React, { useState, useEffect} from "react";
import styles from "./TableReservationPage.module.css";
import UserApi from "../api calls/UserCalls";
import Reservationcalls from "../api calls/Reservationcalls.jsx";


const TableReservationPage = ({ rooms = [] }) => {
    const [users, setUsers] = useState([]);
    const [selectedRoom, setSelectedRoom] = useState("");
    const [selectedTable, setSelectedTable] = useState("");
    const [formData, setFormData] = useState({
        date: "",
        startTime: "",
        endTime: "",
        userId: "",
        reservationType: "INDIVIDUAL"
    });

    const [reservations, setReservations] = useState([]);

    useEffect(() => {
        // Fetch all users when the component mounts
        const fetchUsers = async () => {
            try {
                const fetchedUsers = await UserApi.getAllUsers();
                setUsers(fetchedUsers);
            } catch (error) {
                console.error("Error fetching users:", error);
            }
        };

        fetchUsers();
    }, []);

    useEffect(() => {
        const fetchReservations = async () => {
            try {
                const fetchedReservations = await Reservationcalls.getAllReservationsWeekly(formData?.date);
                setReservations(fetchedReservations);
            } catch (error) {
                console.error("Error fetching reservations:", error);
            }
        };

        if(formData?.date) {
            fetchReservations();
        }
    }, [formData?.date]);

    const handleRoomChange = (e) => {
        setSelectedRoom(rooms.find(room => room.id === Number(e.target.value)));
        setSelectedTable(""); // Reset table selection
        setFormData({ ...formData, startTime: "", endTime: "" });
    };

    const handleTableChange = (e) => {
        setSelectedTable(e.target.value);
        setFormData({ ...formData, startTime: "", endTime: "", tableId: e.target.value });
    };

    const handleDateChange = (e) => {
        setFormData({ ...formData, date: e.target.value });
    };

    const handleTimeChange = (e) => {
        setFormData({ ...formData, [e.target.name]: e.target.value });
    };


    const handleUserChange = (e) => {
        setFormData({ ...formData, userId: e.target.value });
    };

    const handleSubmit = (e) => {
        e.preventDefault();

        Reservationcalls.createReservation(formData)
            .then(() => Reservationcalls.getAllReservationsWeekly(formData?.date).then(data => setReservations(data)))

        alert(`Reserved Table ${selectedTable} in Room ${selectedRoom.name}`);
    };

    const reservedTimes = reservations.filter(
        (res) =>
            res.tableId === parseInt(selectedTable) && res.date === formData.date
    );

    const timeSlots = [];
    for (let hour = 8; hour < 18; hour += 0.5) {
        const slotStartHour = Math.floor(hour);
    const slotStartMinutes = hour % 1 === 0 ? "00" : "30";
    const slotStart = `${slotStartHour.toString().padStart(2, "0")}:${slotStartMinutes}`;

    const slotEndHour = Math.floor(hour + 0.5);
    const slotEndMinutes = (hour + 0.5) % 1 === 0 ? "00" : "30";
    const slotEnd = `${slotEndHour.toString().padStart(2, "0")}:${slotEndMinutes}`;

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
                        value={selectedRoom.id}
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
                        {selectedRoom?.tables?.map((table) => (
                            <option key={table.id} value={table.id}>
                                Table {table.id}
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
                    <select
                        name="reservedBy"
                        value={formData.userId}
                        onChange={handleUserChange}
                        className={styles.select}
                        required
                    >
                        <option value="">Select a User</option>
                        {users.map((user) => (
                            <option key={user.id} value={user.id}>
                                {user.firstName} {user.lastName}
                            </option>
                        ))}
                    </select>
                </label>
                <button type="submit" className={styles.button}>
                    Reserve Table
                </button>
            </form>
        </div>
    );
};

export default TableReservationPage;