import './App.css';
import { BrowserRouter as Router, Routes, Route, useLocation } from 'react-router-dom';
import Navbar from './components/Navbar'; // Existing navbar for login
import MainNavbar from './components/MainNavbar'; // New navbar for other pages
import Login from './components/Login';
import Profile from './components/Profile';
import  Schedule from './components/Schedule.jsx'
import  TableReservations from './components/TableReservations.jsx'
import  Workers from './components/Workers.jsx'


function App() {
    const location = useLocation();

    // Show the existing Navbar for the login page and MainNavbar for all other pages
    const isLoginPage = location.pathname === '/';

    return (
        <div>
            {isLoginPage ? <Navbar /> : <MainNavbar />}
            <Routes>
                <Route path="/" element={<Login />} />
                <Route path="/profile" element={<Profile />} />
                <Route path="/schedule" element={<Schedule />} />
                <Route path="/reserve" element={<TableReservations />} />
                <Route path="/coworkers" element={<Workers />} />
            </Routes>
        </div>
    );
}

function AppWithRouter() {
    return (
        <Router>
            <App />
        </Router>
    );
}

export default AppWithRouter;

