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
    
        console.log('Attempting login with:');
        console.log('Email:', email);
        console.log('Password:', password);
    
        try {
            const response = await AuthCall.signIn({ email, password });
            console.log('Login successful:', response);
    
            const token = response; // Adjust based on your API response structure
            if (!token) {
                setError('No token received.');
                return;
            }
    
            const claims = TokenManager.setAccessToken(token);
    
            if (claims) {
                alert('Login successful! Token stored in sessionStorage.');
                navigate('/profile'); // Redirect to profile page
            } else {
                setError('Invalid token received.');
            }
        } catch (error) {
            // Handle specific error scenarios based on API response
            if (error.response) {
                switch (error.response.status) {
                    case 401:
                        setError('Invalid email or password. Please try again.');
                        break;
                    case 403:
                        setError('Your account is not authorized to log in.');
                        break;
                    default:
                        setError(
                            error.response.data.message || 
                            'An unexpected error occurred. Please try again.'
                        );
                }
            } else if (error.request) {
                setError('Network error. Please check your connection.');
            } else {
                setError('An unexpected error occurred. Please try again.');
            }
            console.error('Login failed:', error);
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
            <div className={styles.loginPage}>
                <div className={styles.loginContainer}>
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
                </div>
            </div>
    );
};

export default Login;
