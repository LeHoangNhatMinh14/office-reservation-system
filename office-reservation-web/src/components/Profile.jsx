import React from 'react';
import styles from '../styles/Profile.module.css';

const Profile = () => {
    return (
        <>
            {/* Navigation */}
            <nav style={{ padding: '1rem', backgroundColor: '#007bff', color: '#fff' }}>
                <h2>Driessen</h2>
            </nav>

            {/* Profile Section */}
            <div className={styles.profileContainer}>
                {/* Picture Section */}
                <div className={styles.picture}>
                    <div className={styles.picBox}>
                        <img src="/profile-pic.jpg" alt="Profile" />
                    </div>
                    <h3>John Doe</h3>
                    <p>Software Developer</p>
                </div>

                {/* Info Section */}
                <div className={styles.info}>
                    <h2>About Me</h2>
                    <p>
                        Hi, I’m John, a passionate software developer with over 5 years of
                        experience in building scalable web applications. I specialize in React,
                        Node.js, and designing seamless user experiences.
                    </p>
                    <h2>Contact Information</h2>
                    <p>Email: johndoe@example.com</p>
                    <p>Phone: +123 456 7890</p>
                    <p>Location: San Francisco, CA</p>
                    <div className={styles.actionButtons}>
                        <button>Send Message</button>
                        <button>Connect</button>
                    </div>
                </div>
            </div>

            {/* Footer */}
           
        </>
    );
};

export default Profile;
