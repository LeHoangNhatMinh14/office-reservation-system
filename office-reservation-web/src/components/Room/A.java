/* General Room Management Container */
.roomManagementContainer {
    max-width: 1000px;
    margin: 50px auto;
    padding: 20px;
    background: #ffffff;
    border-radius: 8px;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
    font-family: Arial, sans-serif;
}

/* Room Form */
.roomForm {
    display: flex;
    gap: 10px;
    margin-bottom: 20px;
}

.inputField {
    padding: 10px;
    border: 1px solid #ccc;
    border-radius: 4px;
    flex: 1;
    font-size: 16px;
}

.addRoomBtn {
    padding: 10px 20px;
    background: #007bff;
    color: white;
    border: none;
    border-radius: 4px;
    font-size: 16px;
    cursor: pointer;
}

.addRoomBtn:hover {
    background: #0056b3;
}

/* Room Header */
.roomHeader {
    display: flex;
    background-color: #f8f9fa;
    padding: 10px 0;
    margin-bottom: 10px;
    border-bottom: 2px solid #ddd;
}

.headerCell {
    flex: 1;
    text-align: center;
    font-weight: bold;
    color: #333;
    padding: 10px;
}

/* Saved Rooms */
.savedRooms {
    margin-top: 20px;
}

.roomCard {
    margin: 10px 0;
    padding: 10px;
    border: 1px solid #ccc;
    border-radius: 4px;
    background: #ffffff;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
    display: flex;
    justify-content: space-between;
    align-items: center;
}

.roomCardHeader {
    font-size: 18px;
    font-weight: bold;
    flex: 2;
}

.roomCardFooter {
    display: flex;
    gap: 10px;
}

.btnEdit,
.btnDelete {
    padding: 5px 10px;
    border: none;
    border-radius: 4px;
    cursor: pointer;
    font-size: 14px;
    font-weight: bold;
}

.btnEdit {
    background: #ffc107;
    color: #333;
}

.btnEdit:hover {
    background: #e0a800;
}

.btnDelete {
    background: #dc3545;
    color: white;
}

.btnDelete:hover {
    background: #c82333;
}

/* Layout Preview */
.layoutPreview {
    margin: 20px 0;
    padding: 20px;
    background: #f8f9fa;
    border-radius: 8px;
    border: 1px solid #ddd;
    text-align: center;
    font-size: 16px;
    color: #666;
}

/* Responsive Design */
@media (max-width: 768px) {
    .roomForm {
        flex-direction: column;
    }

    .headerCell,
    .roomCardHeader {
        text-align: center;
    }
}
