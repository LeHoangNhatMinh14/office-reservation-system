import React from 'react';
import styles from '../styles/Profile.module.css'
const Profile = () => {
    return (
        <div className={styles.profileContainer}>
            <div className={styles.picture}>
            <div className={styles.picBox}>
            </div>
            </div>
            <div className={styles.info}>
                <p>Name</p>
                <p>Name</p>
                <p>Name</p>
                <p>Name</p>
                <p>Name</p>
                <p>Name</p>
            </div>
        </div>
    );
};

export default Profile;
