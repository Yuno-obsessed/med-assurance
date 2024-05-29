import React from 'react';
import {BrowserRouter as Router, Route, Routes, Link, Navigate, Outlet} from 'react-router-dom';
import CreateUserPage from './pages/register/RegisterPage';
import LoginPage from "./pages/login/LoginPage";
import AssurancesPage from "./pages/assurance/AssurancesPage";
import useUserStore from "./store";
import PrivateRoute from "./component/PrivateRoute";
import RegisterPage from "./pages/register/RegisterPage";
import OperationsPage from "./pages/operation/OperationsPage";

const App = () => {
    return (
        <Router>
            <Routes>
                <Route path="/login" element={<LoginPage />} />
                <Route path="/register" element={<RegisterPage />} />
                <Route element={<PrivateRoute />}>
                    <Route path="/" element={<AssurancesPage />} />
                </Route>
            </Routes>
        </Router>
    );
};

export default App;