import React, {useEffect, useState} from 'react';
import './OperationsPage.css';
import Header from "../../component/Header";
import fetchWrapper from "../../api/middleware/auth";
import FloatingErrorMessage from "../../component/ErrorMessage";
import {Link} from "react-router-dom";
import {Pagination} from "../../component/Pagination";


const OperationsPage = () => {
    const [doctorName, setDoctorName] = useState('');
    const [structureName, setStructureName] = useState('');
    const [operationName, setOperationName] = useState('');
    const [offset, setOffset] = useState(0);
    const [limit, setLimit] = useState(10);
    const [isErrorVisible, setIsErrorVisible] = useState(false);
    const [operations, setOperations] = useState([]);
    const [isModalOpen, setIsModalOpen] = useState(false);
    const [totalPages, setTotalPages] = useState(0);

   const handleErrorClose = () => {
        return setIsErrorVisible(false)
   }

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

    const fetchOperation = async () => {
        const data = await fetchWrapper(constructUrl())
        if(!data){
            setIsErrorVisible(true)
        }
        if(!data.operations) {
            setIsErrorVisible(true)
        }
        setOperations(data.operations)
        setTotalPages(data.total_pages);
    }

    const handleModalSubmit = async (event) => {
       event.preventDefault();
       await fetchOperation();
        setOffset(0);
       setIsModalOpen(false)
    }

    useEffect(() => {
        fetchOperation()
    }, [offset])
    return (
        <div className="operations-page">
            <main className="main-content">
                <Header/>
                <div className="filters">
                    <button className="filter-modal" onClick={() => setIsModalOpen(true)}>Filter</button>
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
                                <td>{operation.structure_name}</td>
                                {/*<td>*/}
                                {/*    <Link to={`/structure/${operation.structure_id}`}>*/}
                                {/*        {operation.structure_name}*/}
                                {/*    </Link>*/}
                                {/*</td>*/}
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
                <div style={{marginTop: 20}}>
                <Pagination activePage={offset} totalPages={totalPages} onHandlePage={(i) => {
                    setOffset(i)
                }}/>
                </div>
            </main>
            {isModalOpen && (
                <div className="modal-overlay">
                    <div className="modal">
                        <button className="close-button" onClick={() => setIsModalOpen(!isModalOpen)}>×</button>
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
