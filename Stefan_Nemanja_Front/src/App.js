import React from 'react';
import {BrowserRouter as Router, Navigate, Route, Routes} from 'react-router-dom';
import Login from "./components/Login/Login";
import Register from "./components/Register/Register";
import Strategy from "./components/Strategy/Strategy";
import { jwtDecode } from 'jwt-decode';

const App = () => {
    const isAuthenticated = () => {
        const token = localStorage.getItem('jwt');
        console.log('Token:', token); // Log the token value to debug

        if (!token) {
            console.log('Token is undefined or null, returning false'); // Additional log for clarity
            return false;
        }

        try {
            const parts = token.split('.');
            if (parts.length !== 3) {
                console.error('Invalid token format');
                return false;
            }

            const decodedPayload = atob(parts[1]);
            const parsedPayload = JSON.parse(decodedPayload);
            const { exp } = parsedPayload;

            const currentTime = Math.floor(Date.now() / 1000);
            console.log('Current time:', currentTime);
            console.log('Token expiration time:', exp);

            if (currentTime >= exp) {
                localStorage.removeItem('jwt');
                return false;
            }

            return true;
        } catch (e) {
            console.error('Error decoding token:', e);
            localStorage.removeItem('jwt');
            return false;
        }
    };

    return (
        <Router>
            <Routes>
                <Route path="/login" element={<Login />} />
                <Route path="/register" element={<Register />} />
                <Route path="/" element={isAuthenticated() ? <Strategy/> : <Navigate to='/login'/>}/>
            </Routes>
        </Router>
    );
};

export default App;