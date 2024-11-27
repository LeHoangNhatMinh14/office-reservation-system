import React, { useState, useEffect } from "react";
import styles from "../styles/UserManagement.module.css"; // CSS Module
import UserApi from "./api calls/UserCalls.jsx"; // API service
import { ToastContainer, toast } from "react-toastify";

import "react-toastify/dist/ReactToastify.css"; // Toast styles

const UserManagement = () => {
    const [users, setUsers] = useState([]);
    const [form, setForm] = useState({
        firstName: "",
        lastName: "",
        email: "",
        password: "",
        isAdmin: false,
    });
    const [editingIndex, setEditingIndex] = useState(null);
    const [loading, setLoading] = useState(true);
    const [formSubmitting, setFormSubmitting] = useState(false);

    // Role Mapping for Boolean isAdmin
    const roles = [
        { label: "Admin", value: true },
        { label: "Viewer", value: false },
    ];

    // Fetch Users from Backend
    useEffect(() => {
        const fetchUsers = async () => {
            try {
                const data = await UserApi.getAllUsers();
                setUsers(data);
            } catch (error) {
                toast.error("Error fetching users. Please try again.");
            } finally {
                setLoading(false);
            }
        };

        fetchUsers();
    }, []);

    // Handle Input Changes
    const handleChange = (e) => {
        const { name, value } = e.target;
        setForm({
            ...form,
            [name]: name === "isAdmin" ? value === "true" : value, // Convert role to boolean
        });
    };

    // Handle Form Submission
    const handleSubmit = async (e) => {
        e.preventDefault();
        setFormSubmitting(true);

        try {
            if (editingIndex !== null) {
                // Update User
                const userId = users[editingIndex].id;
                await UserApi.updateUser(userId, form);
                const updatedUsers = [...users];
                updatedUsers[editingIndex] = { ...form, id: userId };
                setUsers(updatedUsers);
                toast.success("User updated successfully!");
                setEditingIndex(null);
            } else {
                // Create User
                const newUser = await UserApi.createUser(form);
                setUsers([...users, newUser]);
                toast.success("User created successfully!");
            }
            setForm({
                firstName: "",
                lastName: "",
                email: "",
                password: "",
                isAdmin: false,
            });
        } catch (error) {
            toast.error("Error saving user. Please try again.");
        } finally {
            setFormSubmitting(false);
        }
    };

    // Handle Edit
    const handleEdit = (index) => {
        setEditingIndex(index);
        setForm(users[index]);
    };

    // Handle Delete
    const handleDelete = async (index) => {
        const userId = users[index].id;

        try {
            await UserApi.deleteUser(userId);
            setUsers(users.filter((_, i) => i !== index));
            toast.success("User deleted successfully!");
        } catch (error) {
            toast.error("Error deleting user. Please try again.");
        }
    };

    return (
        <div className={styles.userManagementContainer}>
            <ToastContainer />
            <h1 className={styles.title}>User Management</h1>

            {/* User Form */}
            <form onSubmit={handleSubmit} className={styles.form}>
                <input
                    type="text"
                    name="firstName"
                    value={form.firstName}
                    onChange={handleChange}
                    placeholder="First Name"
                    className={styles.input}
                    required
                />
                <input
                    type="text"
                    name="lastName"
                    value={form.lastName}
                    onChange={handleChange}
                    placeholder="Last Name"
                    className={styles.input}
                    required
                />
                <input
                    type="email"
                    name="email"
                    value={form.email}
                    onChange={handleChange}
                    placeholder="Email"
                    className={styles.input}
                    required
                />
                <input
                    type="password"
                    name="password"
                    value={form.password}
                    onChange={handleChange}
                    placeholder="Password"
                    className={styles.input}
                    required
                />
                <select
                    name="isAdmin"
                    value={form.isAdmin}
                    onChange={handleChange}
                    className={styles.select}
                    required
                >
                    <option value="" disabled>
                        Select Role
                    </option>
                    {roles.map((role) => (
                        <option key={role.value} value={role.value}>
                            {role.label}
                        </option>
                    ))}
                </select>
                <button type="submit" className={styles.submitButton} disabled={formSubmitting}>
                    {formSubmitting
                        ? editingIndex !== null
                            ? "Updating..."
                            : "Adding..."
                        : editingIndex !== null
                            ? "Update User"
                            : "Add User"}
                </button>
            </form>

            {/* User List */}
            <h2 className={styles.subtitle}>User List</h2>
            {loading ? (
                <p className={styles.loading}>Loading users...</p>
            ) : users.length > 0 ? (
                <table className={styles.table}>
                    <thead>
                    <tr>
                        <th>First Name</th>
                        <th>Last Name</th>
                        <th>Email</th>
                        <th>Role</th>
                        <th>Actions</th>
                    </tr>
                    </thead>
                    <tbody>
                    {users.map((user, index) => (
                        <tr key={user.id}>
                            <td>{user.firstName}</td>
                            <td>{user.lastName}</td>
                            <td>{user.email}</td>
                            <td>{user.isAdmin ? "Admin" : "Viewer"}</td>
                            <td>
                                <button
                                    onClick={() => handleEdit(index)}
                                    className={styles.editButton}
                                >
                                    Edit
                                </button>
                                <button
                                    onClick={() => handleDelete(index)}
                                    className={styles.deleteButton}
                                >
                                    Delete
                                </button>
                            </td>
                        </tr>
                    ))}
                    </tbody>
                </table>
            ) : (
                <p className={styles.noData}>No users available. Add a user to get started.</p>
            )}
        </div>
    );
};

export default UserManagement;
