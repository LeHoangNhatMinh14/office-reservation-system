import React, { useState, useEffect } from "react";
import styles from "../styles/UserManagement.module.css"; // Import the CSS module
import axios from "axios"; // For fetching data

const UserManagement = () => {
    const [users, setUsers] = useState([]);
    const [form, setForm] = useState({
        firstName: "",
        lastName: "",
        email: "",
        role: "",
    });
    const [editingIndex, setEditingIndex] = useState(null);
    const [loading, setLoading] = useState(true);

    const roles = ["Admin", "Editor", "Viewer"]; // Predefined roles

    // Fetch users on mount
    useEffect(() => {
        const fetchUsers = async () => {
            try {
                const response = await axios.get("https://jsonplaceholder.typicode.com/users");
                const fetchedUsers = response.data.map((user) => ({
                    id: user.id,
                    firstName: user.name.split(" ")[0],
                    lastName: user.name.split(" ")[1] || "",
                    email: user.email,
                    role: roles[Math.floor(Math.random() * roles.length)],
                }));
                setUsers(fetchedUsers);
                setLoading(false);
            } catch (error) {
                console.error("Error fetching users:", error);
                setLoading(false);
            }
        };

        fetchUsers();
    }, []);

    // Handle input changes
    const handleChange = (e) => {
        const { name, value } = e.target;
        setForm({ ...form, [name]: value });
    };

    // Handle form submission
    const handleSubmit = (e) => {
        e.preventDefault();
        if (editingIndex !== null) {
            // Update user
            const updatedUsers = [...users];
            updatedUsers[editingIndex] = form;
            setUsers(updatedUsers);
            setEditingIndex(null);
        } else {
            // Create new user
            setUsers([...users, { ...form, id: Date.now() }]);
        }
        setForm({ firstName: "", lastName: "", email: "", role: "" }); // Reset form
    };

    // Handle edit
    const handleEdit = (index) => {
        setEditingIndex(index);
        setForm(users[index]);
    };

    // Handle delete
    const handleDelete = (index) => {
        const updatedUsers = users.filter((_, i) => i !== index);
        setUsers(updatedUsers);
    };

    return (
        <div className={styles.userManagementContainer}>
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
                <select
                    name="role"
                    value={form.role}
                    onChange={handleChange}
                    className={styles.select}
                    required
                >
                    <option value="" disabled>
                        Select Role
                    </option>
                    {roles.map((role, index) => (
                        <option key={index} value={role}>
                            {role}
                        </option>
                    ))}
                </select>
                <button type="submit" className={styles.submitButton}>
                    {editingIndex !== null ? "Update User" : "Add User"}
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
                                <td>{user.role}</td>
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
                <p className={styles.noData}>
                    No users available. Add a user to get started.
                </p>
            )}
        </div>
    );
};

export default UserManagement;
