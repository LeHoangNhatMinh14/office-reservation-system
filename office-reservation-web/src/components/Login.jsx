import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { GoogleOAuthProvider, GoogleLogin } from '@react-oauth/google';
import AuthCall from '../components/api calls/AuthCall';
import TokenManager from '../components/api calls/TokenManager'; // Import TokenManager for token management
import styles from '../styles/Login.module.css';

const Login = () => {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState(null);
    const navigate = useNavigate();

    const handleLogin = async (e) => {
        e.preventDefault();
        setError(null); // Reset error state
    
        // Log credentials to the console for debugging
        console.log('Attempting login with:');
        console.log('Email:', email);
        console.log('Password:', password);
    
        try {
            const response = await AuthCall.signIn({ email, password });
            console.log('Login successful:', response);
            
            // Assuming response.data contains the access token
            const token = response.data; // Replace with actual token field
            const claims = TokenManager.setAccessToken(token); // Set token in TokenManager

            if (claims) {
                alert('Login successful! Token stored in sessionStorage.');
                navigate('/profile');
            } else {
                setError('Invalid token received.');
            }
        } catch (error) {
            console.error('Login failed:', error);
            setError(error.response?.data || 'Login failed. Please try again.');
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
                <div className={styles.loginContainer}>
                    <img src="/path/to/driessen-logo.jpg" alt="Driessen Logo" className={styles.logo} />
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
                    {error && <p style={{ color: 'red' }}>{error}</p>}
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
