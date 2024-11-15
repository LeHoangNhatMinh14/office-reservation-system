import React from "react";
import { Link } from "react-router-dom"; // Link component from react-router-dom
import styles from '../styles/Navbar.module.css';

function Navbar() {
    return (
        <nav className={styles.navBar}>
            <h1 className={styles.logo}>driessen</h1>
        </nav>
    );
}

export default Navbar;
