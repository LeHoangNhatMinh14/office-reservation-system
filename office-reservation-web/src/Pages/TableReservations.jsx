import React, { useState } from "react";
import ReservationForm from "../components/reservations/ReservationForm.jsx";
import TableGrid from "../components/reservations/TableGrid.jsx";
import styles from "../styles/TableReserv.module.css"
import axios from "axios";

const TableReservations = () => {
    const [selectedRoom, setSelectedRoom] = useState(null);
    const [selectedTable, setSelectedTable] = useState(null);

    const handleReservationSubmit = ({ roomId, tableId, date, time }) => {
        const reservationData = {
            roomId,
            tableId,
            date,
            time
        };

        // Send POST request to make the reservation
        axios.post("/reservations", reservationData)
            .then((response) => {
                console.log("Reservation successful", response.data);
                setSelectedRoom(roomId);
                setSelectedTable(tableId);
            })
            .catch((error) => {
                console.error("Error making reservation", error);
            });
    };

    return (
        <div className={styles.reservationContainer}>
            <ReservationForm onSubmit={handleReservationSubmit} />
            {selectedRoom && <TableGrid roomId={selectedRoom} onBook={setSelectedTable} />}
        </div>
    );
};

export default TableReservations;
