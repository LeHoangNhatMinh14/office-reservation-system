import React, {useState} from 'react';
import styles from '../styles/Navbar.module.css';
import {Link} from "react-router-dom";
import logo from '../assets/driessen.png';

const MainNavbar = () => {

    //add a parameter checking for manager/admin so a different
    return (
        <nav className={styles.navBar}>
            <img src={logo} alt="" />
            <ul className={styles.navLinks}>
                <li><Link to="/usermanagement ">User</Link></li>
                <li><Link to="/roommanagement">Room</Link></li>
                <li><Link to="/schedule">Schedule</Link></li>
                <li><Link to="/teams">Teams</Link></li>
                <li><Link to="/reserve">Reservation</Link></li>
            </ul>
        </nav>
    );
};

export default MainNavbar;
