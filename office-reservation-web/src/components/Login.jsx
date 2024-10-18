import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import styles from '../styles/Login.module.css'
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

    return (
        <div>
            <div className={styles.loginContainer}>
                <h1 className={styles.formTitle}>Login</h1>
                <form onSubmit={handleLogin} className={styles.form}>
                    <div>
                        <input
                            type="email"
                            placeholder="Email"
                            value={email}
                            onChange={(e) => setEmail(e.target.value)}
                            className={styles.input}
                        />
                    </div>
                    <div>
                        <input
                            type="password"
                            placeholder="Password"
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                            className={styles.input}
                        />
                    </div>
                    <button type="submit" className={styles.button}>Login</button>
                </form>
            </div>
        </div>
    );
};

export default Login;
