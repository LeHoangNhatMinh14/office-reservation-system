import React, { useState } from "react";
import UserForm from "./UserForm";
import UserTable from "./UserTable";
import "./styles/UserManagement.css";

const UserManagement = () => {
    const [users, setUsers] = useState([]);
    const [editingUser, setEditingUser] = useState(null);

    // Add new user
    const addUser = (user) => {
        setUsers([...users, user]);
    };

    // Update existing user
    const updateUser = (updatedUser) => {
        setUsers(users.map((user) => (user.id === updatedUser.id ? updatedUser : user)));
        setEditingUser(null);
    };

    // Delete user
    const deleteUser = (id) => {
        setUsers(users.filter((user) => user.id !== id));
    };

    return (
        <div className="user-management-container">
            <h1>User Management</h1>
            <UserForm
                addUser={addUser}
                updateUser={updateUser}
                editingUser={editingUser}
                setEditingUser={setEditingUser}
            />
            <UserTable users={users} setEditingUser={setEditingUser} deleteUser={deleteUser} />
        </div>
    );
};

export default UserManagement;
