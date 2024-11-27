import React from "react";
import { Link } from "react-router-dom"; // Link component from react-router-dom
import styles from '../styles/Navbar.module.css';
import logo from '../assets/driessen.png';

function Navbar() {
    return (
        <nav className={styles.navBar}>
            <img src={logo} alt="" />
            <ul className={styles.navLinks}>


                <li><Link to="/roommanagement">LOGIN</Link></li>
            </ul>
        </nav>
    );
}

export default Navbar;
