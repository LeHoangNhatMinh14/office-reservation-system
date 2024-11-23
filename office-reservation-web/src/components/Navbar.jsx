import React from "react";
import { Link } from "react-router-dom"; // Link component from react-router-dom
import styles from '../styles/Navbar.module.css';
import logo from '../assets/driessen.png';

function Navbar() {
    return (
        <nav className={styles.navBar}>
            <img src={logo} alt="" />
            <ul className={styles.navLinks}>
                <li><Link to="/usermanagement ">User</Link></li>
                <li><Link to="/room">Room</Link></li>
                <li><Link to="/schedule">Schedule</Link></li>
                <li><Link to="/teams">Teams</Link></li>
                <li><Link to="/reservation">Reservation</Link></li>
                <li><Link to="/logout">Logout</Link></li>
            </ul>
        </nav>
    );
}

export default Navbar;
