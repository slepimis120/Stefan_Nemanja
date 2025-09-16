import React from 'react';
import { Navigate, Outlet, useLocation } from 'react-router-dom';

const Authentication = ({ children }) => {
    const location = useLocation();

    const isAuthenticated = () => {
        const token = localStorage.getItem('jwt');
        if (!token) return false;

        try {
            const parts = token.split('.');
            if (parts.length !== 3) return false;

            const decoded = atob(parts[1]);
            const payload = JSON.parse(decoded);
            const now = Math.floor(Date.now() / 1000);

            if (payload.exp && now < payload.exp) return true;
            return false;
        } catch (e) {
            console.error('Authentication: token decode error', e);
            return false;
        }
    };

    if (!isAuthenticated()) {
        return <Navigate to="/login" replace state={{ from: location }} />;
    }

    return children ?? <Outlet />;
};

export default Authentication;