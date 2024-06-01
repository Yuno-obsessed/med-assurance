import React, {useEffect, useState} from "react";
import fetchWrapper from "../../api/middleware/auth";
import Header from "../../component/Header";
import './DoctorPage.css';
import {useLocation, useParams} from "react-router-dom";

const DoctorPage = () => {
    const [doctor, setDoctor] = useState(null);
    const url = useLocation();
    const { doctorID } = useParams();

    useEffect(() => {
        const fetchDoctor = async () => {
            try {
                const data = await fetchWrapper(`http://localhost:8080/api/v1/med-ass/doctor/${doctorID}`, {
                    method: 'GET'
                });
                if (data) {
                    setDoctor(data)
                    console.log(data)
                }
            } catch (error) {
                console.error('Error fetching a doctor:', error);
            }
        };
        fetchDoctor();
    }, []);

    return (
        <div className="doctor-page">
            <Header />
            <main className="doctor-content">
                {doctor ? (
                    <>
                        <div className="doctor-info">
                            <img src={`https://randomuser.me/api/portraits/women/${doctorID}.jpg`} alt="Doctor" width="150"/>
                            <div className="doctor-details">
                                <h3>{doctor.name}</h3>
                                <h2>{doctor.specialty}</h2>
                                <h4>+380 67 189 4189</h4>
                            </div>
                        </div>
                        <div className="operations-list">
                            {doctor.operations.length > 0 ? (
                                doctor.operations.map((operation, index) => (
                                    <div key={index} className="operation-card">
                                        <h4>{operation.name}</h4>
                                        <p>{operation.description}</p>
                                    </div>
                                ))
                            ) : (
                                <p>No operations found</p>
                            )}
                        </div>
                    </>
                ) : (
                    <p>Loading...</p>
                )}
            </main>
        </div>
    );
};

export default DoctorPage;
