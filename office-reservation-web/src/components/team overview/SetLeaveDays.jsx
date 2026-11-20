import React, { useState } from "react";
import styles from "../../styles/SetLeaveDays.module.css";
import LeaveDaysApi from "../api calls/LeaveDaysCalls"; // Import your API class
import TokenManager from "../api calls/TokenManager"; // Import the TokenManager

const SetLeaveDays = () => {
    const [startDate, setStartDate] = useState("");
    const [endDate, setEndDate] = useState("");
    const [reason, setReason] = useState("");

    const handleSubmit = async (e) => {
        e.preventDefault();

        // Check if all fields are filled out
        if (!startDate || !endDate || !reason.trim()) {
            alert("Please fill out all fields!");
            return;
        }

        // Retrieve userId from TokenManager
        const userId = TokenManager.getUserId();
        if (!userId) {
            alert("Unable to determine user. Please log in again.");
            TokenManager.clear(); // Clear any stale session
            return;
        }

        // Prepare leave data payload
        const leaveData = {
            userId, // Include the userId
            startDate,
            endDate,
            reason,
        };

        try {
            console.log("Sending leave data:", leaveData);
            const response = await LeaveDaysApi.createLeaveDays(leaveData);
            console.log("Leave request created:", response);
            alert("Leave request submitted successfully!");
            // Reset form fields
            setStartDate("");
            setEndDate("");
            setReason("");
        } catch (error) {
            console.error("Error submitting leave request:", error.response?.data || error.message);
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
