import React from 'react';
import LoginPage from "./pages/login/LoginPage";
import AssurancesPage from "./pages/assurance/AssurancesPage";
import PrivateRoute from "./component/PrivateRoute";
import RegisterPage from "./pages/register/RegisterPage";
import {Navigate, Route, Routes} from "react-router-dom";
import {Navigation} from "./component/Navigation";
import RefundsPage from "./pages/refund/RefundsPage";
import OperationsPage from "./pages/operation/OperationsPage";
import {useAuthStore} from "./store/auth";
import DoctorPage from "./pages/doctor/DoctorPage";

const App = () => {
    const {isAuth} = useAuthStore();
    return (
        <div style={{
            display: "flex",
        }}>
            {isAuth && (
                <Navigation/>
            )}
            <div style={{
                width: "100%",
            }}>
            <Routes>
                <Route path="/login" element={<LoginPage />} />
                <Route path="/register" element={<RegisterPage />} />
                <Route element={<PrivateRoute />}>
                    <Route path="/" element={<AssurancesPage />} />
                </Route>
                <Route element={<PrivateRoute />}>
                    <Route path="/refund" element={<RefundsPage />} />
                </Route>
                <Route element={<PrivateRoute />}>
                    <Route path="/operations" element={<OperationsPage />} />
                </Route>
                <Route element={<PrivateRoute />}>
                    <Route path="/doctor/:doctorID" element={<DoctorPage />} />
                </Route>
            </Routes>
            </div>
        </div>
    );
};

export default App;