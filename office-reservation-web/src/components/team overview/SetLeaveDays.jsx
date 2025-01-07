import React, { useState } from "react";
import styles from "../../styles/SetLeaveDays.module.css";
import LeaveDaysApi from "../api calls/LeaveDaysCalls"; // Import your API class

const SetLeaveDays = () => {
    const [startDate, setStartDate] = useState("");
    const [endDate, setEndDate] = useState("");
    const [reason, setReason] = useState("");

    const handleSubmit = async (e) => {
        e.preventDefault();

        if (!startDate || !endDate || !reason.trim()) {
            alert("Please fill out all fields!");
            return;
        }

        const leaveData = {
            startDate,
            endDate,
            reason,
        };

        try {
            const response = await LeaveDaysApi.createLeaveDays(leaveData);
            console.log("Leave request created:", response);
            alert("Leave request submitted successfully!");
            // Reset form
            setStartDate("");
            setEndDate("");
            setReason("");
        } catch (error) {
            console.error("Error submitting leave request:", error);
            alert("Failed to submit leave request. Please try again.");
        }
    };

    return (
        <div className={styles.leaveFormContainer}>
            <h2 className={styles.formTitle}>Set Leave Days</h2>
            <form className={styles.leaveForm} onSubmit={handleSubmit}>
                <div className={styles.formGroup}>
                    <label htmlFor="startDate">Start Date:</label>
                    <input
                        type="date"
                        id="startDate"
                        value={startDate}
                        onChange={(e) => setStartDate(e.target.value)}
                        required
                    />
                </div>
                <div className={styles.formGroup}>
                    <label htmlFor="endDate">End Date:</label>
                    <input
                        type="date"
                        id="endDate"
                        value={endDate}
                        onChange={(e) => setEndDate(e.target.value)}
                        required
                    />
                </div>
                <div className={styles.formGroup}>
                    <label htmlFor="reason">Reason:</label>
                    <textarea
                        id="reason"
                        value={reason}
                        onChange={(e) => setReason(e.target.value)}
                        placeholder="Enter your reason for leave"
                        required
                    />
                </div>
                <button type="submit" className={styles.submitBtn}>
                    Submit
                </button>
            </form>
        </div>
    );
};

export default SetLeaveDays;