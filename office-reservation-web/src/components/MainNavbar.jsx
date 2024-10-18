import React, {useState} from 'react';
import styles from '../styles/MainNavbar.module.css'; // You can create a new CSS file for this
import { useNavigate } from 'react-router-dom';
const MainNavbar = () => {
    const navigate = useNavigate();

    const changePage = (button) => {
        if(button === 'profile'){
            navigate('/profile');
        } else if(button === 'schedule'){
            navigate('/schedule');
        } else if(button === 'reserve'){
            navigate('/reserve');
        } else if(button === 'coworkers'){
            navigate('/coworkers');
        }
    };
    //add a parameter checking for manager/admin so a different
    return (
        <nav className={styles.navBarMain}>
            <div className={styles.logoHolder}>
                <h1 className={styles.logo}>driessen</h1>
            </div>
            <div className={styles.buttonContainer}>
                <button className={styles.profile} onClick={()=> changePage('profile')}>Profile</button>
                <button className={styles.schedule} onClick={()=> changePage('schedule')}>Schedule</button>
                <button className={styles.reserve} onClick={()=> changePage('reserve')}>Reserve</button>
                <button className={styles.yourCoworkers} onClick={()=> changePage('coworkers')}>Your Coworkers</button>
            </div>
        </nav>
    );
};

export default MainNavbar;
