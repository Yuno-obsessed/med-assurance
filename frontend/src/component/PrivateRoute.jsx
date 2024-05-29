import React from 'react';
import {Navigate, Outlet} from 'react-router-dom';
import {useUserStore} from '../store/user'; // Adjust the path based on your project structure

const PrivateRoute = () => {
    const { userData } = useUserStore();

    return userData ? <Outlet /> : <Navigate to="/login" />;
};

export default PrivateRoute;