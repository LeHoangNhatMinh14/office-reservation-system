import React, { useState, useEffect } from "react";
import styles from "../../styles/TableReserv.module.css"
import axios from "axios";

const TableGrid = ({ roomId, onBook }) => {
    const [reservations, setReservations] = useState([]);

    useEffect(() => {
        if (roomId) {
            axios.get(`/reservations?roomId=${roomId}`)
                .then((response) => setReservations(response.data))
                .catch((error) => console.error("Error fetching reservations", error));
        }
    }, [roomId]);

    const isTableAvailable = (tableId) => {
        return !reservations.some((res) => res.table.id === tableId);
    };

    return (
        <div className={styles.tableGrid}>
            {reservations.map((reservation) => (
                <div
                    key={reservation.table.id}
                    className={`table ${isTableAvailable(reservation.table.id) ? "free" : "occupied"}`}
                    onClick={() => onBook(reservation.table.id)}
                >
                    Table {reservation.table.islandNumber}
                </div>
            ))}
        </div>
    );
};

export default TableGrid;
