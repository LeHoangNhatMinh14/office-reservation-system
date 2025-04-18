import React, {useState} from 'react';
import styles from '../styles/Navbar.module.css';
import {Link} from "react-router-dom";
const MainNavbar = () => {

    //add a parameter checking for manager/admin so a different
    return (
        <nav className={styles.navBar}>
            <h1 className={styles.logo}>driessen</h1>
            <ul className={styles.navLinks}>
                <li><Link to="/room">Room</Link></li>
                <li><Link to="/schedule">Schedule</Link></li>
                <li><Link to="/teams">Teams</Link></li>
                <li><Link to="/reservation">Reservation</Link></li>
            </ul>
        </nav>
    );
};

export default MainNavbar;
