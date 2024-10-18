import styles from "../styles/TableReserv.module.css";
import React from "react";
import SpecsInput from "./SpecsInput.jsx";

const TableReservations = () => {
    return(
        <div>
            <div className={styles.reservationContainer}>
                <div className={styles.specifications}>
                    <p className={styles.specs}>Floor:</p>
                    <SpecsInput/>
                    <p className={styles.specs}>Room:</p>
                    <SpecsInput/>
                    <p className={styles.specs}>Table:</p>
                    <SpecsInput/>
                    <p className={styles.specs}>Date:</p>
                    <SpecsInput/>
                    <button className={styles.applySpecs}>Apply</button>
                </div>
                <div className={styles.tables}>
                    <h1 className={styles.title}>Facility</h1>
                    <button className={styles.tablesToRes}>Floor : Room - Table</button>
                    <button className={styles.tablesToRes}>Floor : Room - Table</button>
                    <button className={styles.tablesToRes}>Floor : Room - Table</button>
                    <button className={styles.tablesToRes}>Floor : Room - Table</button>
                    <button className={styles.tablesToRes}>Floor : Room - Table</button>
                    <button className={styles.tablesToRes}>Floor : Room - Table</button>
                    <button className={styles.tablesToRes}>Floor : Room - Table</button>
                    <button className={styles.tablesToRes}>Floor : Room - Table</button>
                    <button className={styles.tablesToRes}>Floor : Room - Table</button>
                    <button className={styles.tablesToRes}>Floor : Room - Table</button>
                </div>
                <div className={styles.schedule}>
                    <h1 className={styles.titleS}>Name</h1>
                    <p>Name</p>
                    <p>Name</p>
                    <p>Name</p>
                    <p>Name</p>
                    <p>Name</p>
                    <p>Name</p>
                </div>
            </div>
        </div>
    );
}
export default TableReservations;