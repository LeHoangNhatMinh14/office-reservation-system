import './App.css';
import { BrowserRouter as Router, Routes, Route, useLocation } from 'react-router-dom';
import Navbar from './components/Navbar'; // Existing navbar for login
import MainNavbar from './components/MainNavbar'; // New navbar for other pages
import Footer from './components/Footer'; // Importing the footer
import Login from './components/Login';
import Profile from './components/Profile';
import Schedule from './components/Schedule';

import Workers from './components/Workers';
import RoomManagement from "./components/Room/RoomManagement";
import UserManagement from './components/UserManagement';

import TeamOverview from './pages/TeamOverview';
import RoomReservation from "./components/Room/RoomReservation.jsx";
import TableReservationPage from './components/Room/TableReservationPage';

function App() {
    const location = useLocation();

    // Show the Navbar for the login page and MainNavbar for all other pages
    const isLoginPage = location.pathname === '/';

    return (
        <div>
            {isLoginPage ? <Navbar /> : <MainNavbar />}

            {/* Main content with routes */}
            <Routes>
                <Route path="/" element={<Login />} />
                <Route path="/profile" element={<Profile />} />
                <Route path="/schedule" element={<Schedule />} />
                <Route path="/reserve" element={< TableReservationPage/>} />
                <Route path="/coworkers" element={<Workers />} />
                <Route path="/tablereservation" element={<TableReservationPage />} />
                <Route path="/usermanagement" element={<UserManagement/>}/>
                <Route path="/teams" element={<TeamOverview />} />
                <Route path="/roommanagement" element={<RoomManagement />} />
            </Routes>

            {/* Footer should be displayed on all pages */}
            <Footer />
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
