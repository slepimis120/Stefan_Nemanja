import React from 'react';

import {BrowserRouter as Router, Navigate, Route, Routes} from 'react-router-dom';

import Login from "./components/Login/Login";
import Register from "./components/Register/Register";
import Strategy from "./components/Strategy/Strategy";

const App = () => {
    const isAuthenticated = () => {
        const token = localStorage.getItem('jwt');
        if (!token) return false;

        const expiry = JSON.parse(atob(token.split('.')[1])).exp;
        return expiry * 1000 > Date.now();
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