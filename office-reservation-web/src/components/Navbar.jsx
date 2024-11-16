import React from "react";
import { Link } from "react-router-dom"; // Link component from react-router-dom
import styles from '../styles/Navbar.module.css';

function Navbar() {
    return (
        <nav className={styles.navBar}>
            <h1 className={styles.logo}>driessen</h1>
<<<<<<< HEAD
            <ul className={styles.navLinks}>
                <li><Link to="/room">Room</Link></li>
                <li><Link to="/schedule">Schedule</Link></li>
                <li><Link to="/teams">Teams</Link></li>
                <li><Link to="/reservation">Reservation</Link></li>
                
            </ul>
=======
>>>>>>> dev
        </nav>
    );
}

export default Navbar;
