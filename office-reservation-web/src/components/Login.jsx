import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { GoogleOAuthProvider, GoogleLogin } from '@react-oauth/google';
import styles from '../styles/Login.module.css';

const Login = () => {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const navigate = useNavigate();

    const handleLogin = (e) => {
        e.preventDefault();
        if (email === 'showcase@email' && password === 'password') {
            navigate('/profile');
        } else {
            alert('Invalid credentials');
        }
    };

    const handleGoogleLoginSuccess = (response) => {
        console.log('Google login successful:', response);
        navigate('/profile');
    };

    const handleGoogleLoginFailure = (error) => {
        console.log('Google login failed:', error);
        alert('Google login failed, please try again.');
    };

    return (
        <GoogleOAuthProvider clientId="YOUR_GOOGLE_CLIENT_ID">
            <div className={styles.loginPage}>
                {/* Navigation Bar */}
              {/* <nav className={styles.navbar}>
                    <a href="/">Home</a>
                    <a href="/signup">Sign Up</a>
                    <a href="/help">Help</a>
                </nav>*/ }
                <div className={styles.loginContainer}>
                    {/* Logo */}
                    <img src="/path/to/driessen-logo.jpg" alt="Driessen Logo" className={styles.logo} />
                    {/* Login Form */}
                    <h1 className={styles.formTitle}>Login</h1>
                    <form onSubmit={handleLogin} className={styles.form}>
                        <input
                            type="email"
                            placeholder="Email"
                            value={email}
                            onChange={(e) => setEmail(e.target.value)}
                            className={styles.input}
                        />
                        <input
                            type="password"
                            placeholder="Password"
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                            className={styles.input}
                        />
                        <button type="submit" className={styles.button}>Login</button>
                    </form>
                    <div className={styles.ssoSection}>
                        <GoogleLogin
                            onSuccess={handleGoogleLoginSuccess}
                            onError={handleGoogleLoginFailure}
                            useOneTap
                        />
                    </div>
                </div>
            </div>
        </GoogleOAuthProvider>
    );
};

export default Login;
