import React from 'react';
import {BrowserRouter as Router, Navigate, Route, Routes} from 'react-router-dom';
import Login from "./components/Login/Login";
import Register from "./components/Register/Register";
import Strategy from "./components/Strategy/Strategy";
import Authentication from "./components/Authentication/Authentication";

const App = () => {
    return (
        <Router>
            <Routes>
                <Route path="/login" element={<Login />} />
                <Route path="/register" element={<Register />} />
                <Route element={<Authentication />}>
                    <Route path="/home" element={<Strategy />} />
                </Route>
                <Route path="*" element={<Navigate to="/home" replace />} />
            </Routes>
        </Router>
    );
};

export default App;