import React from "react";
import styles from '../styles/Footer.module.css';

function Footer() {
    return (
        <footer className={styles.footer}>
            <a href="#">Privacy Policy</a>
            <a href="#">Terms of Service</a>
            <a href="#">Contact</a>
        </footer>
    );
}

export default Footer;
