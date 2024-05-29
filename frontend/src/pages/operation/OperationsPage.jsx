import React, {useEffect, useState} from 'react';
import './OperationsPage.css';
import Header from "../../component/Header";
import fetchWrapper from "../../api/middleware/auth";
import FloatingErrorMessage from "../../component/ErrorMessage";
import {Link} from "react-router-dom";


const OperationsPage = () => {
    const [doctorName, setDoctorName] = useState('');
    const [structureName, setStructureName] = useState('');
    const [operationName, setOperationName] = useState('');
    const [offset, setOffset] = useState(0);
    const [limit, setLimit] = useState(10);
    const [operations, setOperations] = useState([]);
    const [isModalOpen, setIsModalOpen] = useState(false);

    useEffect(() => {
        const fetchOperations = async () => {
            try {
                const data = await fetchWrapper(`http://localhost:8080/api/v1/med-ass/operation/search?doctorName=${doctorName}&structureName=${structureName}&operationName=${operationName}`);
                setOperations(data);
            } catch (error) {
                setIsErrorVisible(true);
                console.error('Error fetching operations:', error);
            }
        };
        fetchOperations();
    }, []);

    const [isErrorVisible, setIsErrorVisible] = useState(false);

    const handleErrorClose = () => {
        setIsErrorVisible(false);
    };

    const handleFilterClick = () => {
        setIsModalOpen(true);
    };

    const handleCloseModal = () => {
        setIsModalOpen(false);
        setStructureName('');
        setDoctorName('');
        setOperationName('');
    };

    function constructUrl() {
        let baseUrl = 'http://localhost:8080/api/v1/med-ass/operation/search?';
        if (doctorName) {
            baseUrl += `doctorName=${doctorName}&`;
        }
        if (structureName) {
            baseUrl += `structureName=${structureName}&`;
        }
        if (operationName) {
            baseUrl += `operationName=${operationName}&`;
        }
        baseUrl += `offset=${offset}&`;
        baseUrl += `limit=${limit}&`;
        return baseUrl;
    }

    const handleModalSubmit = async (data) => {
        data.preventDefault();

        // const response = await fetchWrapper(`http://localhost:8080/api/v1/med-ass/operation/search?doctorName=${doctorName}&structureName=${structureName}&operationName=${operationName}&offset=${offset}&limit=${limit}`, {
        const response = await fetchWrapper(constructUrl(), {
            headers: {
                "Accept": "application/json",
                "Content-Type": "application/json",
            },
        });
        if (!response) {
            setIsErrorVisible(true);
        } else {
            console.log(response);
            setOperations(response);
        }
        handleCloseModal();
    };

    return (
        <div className="operations-page">
            <main className="main-content">
                <Header></Header>
                <div className="filters">
                    <button className="filter-modal" onClick={handleFilterClick}>Filter</button>
                </div>
                <table className="operations-table">
                    <thead>
                    <tr>
                        <th>Structure name</th>
                        <th>Doctor name</th>
                        <th>Operation name</th>
                        <th>Price</th>
                    </tr>
                    </thead>
                    <tbody>
                    {operations.length > 0 ? (
                        operations.map((operation, index) => (
                            <tr key={index}>
                                <td>
                                    <Link to={`/structure/${operation.structure_id}`}>
                                        {operation.structure_name}
                                    </Link>
                                </td>
                                <td>
                                    <Link to={`/doctor/${operation.doctor_id}`}>
                                        {operation.doctor_name}
                                    </Link>
                                </td>
                                <td>{operation.operation_name}</td>
                                <td>{operation.price} €</td>
                            </tr>
                        ))
                    ) : (
                        <tr>
                            <td colSpan="5">No operations found</td>
                        </tr>
                    )}
                    </tbody>
                </table>
            </main>
            {isModalOpen && (
                <div className="modal-overlay">
                    <div className="modal">
                        <button className="close-button" onClick={handleCloseModal}>×</button>
                        <h2>Filter operations</h2>
                        <form onSubmit={handleModalSubmit}>
                            <div className="modal-input">
                                <label>Structure name:</label>
                                <input type="text" value={structureName} onChange={(e) => setStructureName(e.target.value)} />
                            </div>
                            <div className="modal-input">
                                <label>Doctor name:</label>
                                <input type="text" value={doctorName} onChange={(e) => setDoctorName(e.target.value)} />
                            </div>
                            <div className="modal-input">
                                <label>Operation name:</label>
                                <input type="text" value={operationName} onChange={(e) => setOperationName(e.target.value)} />
                            </div>
                            <button className="submit-button" type="submit">Submit</button>
                        </form>

                    </div>
                </div>
            )}            {isErrorVisible && (
                <FloatingErrorMessage
                    message="An error occurred! Please try again."
                    onClose={handleErrorClose}
                />
            )}
        </div>
    );
};

export default OperationsPage;
