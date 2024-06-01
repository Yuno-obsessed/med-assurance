import React, {useEffect, useState} from 'react';
import './RefundsPage.css';
import Header from "../../component/Header";
import fetchWrapper from "../../api/middleware/auth";
import FloatingErrorMessage from "../../component/ErrorMessage";
import {Pagination} from "../../component/Pagination";


const RefundsPage = () => {
    const [offset, setOffset] = useState(0);
    const [limit, setLimit] = useState(10);
    const [isErrorVisible, setIsErrorVisible] = useState(false);
    const [refunds, setRefunds] = useState([]);
    const [totalPages, setTotalPages] = useState(0);
    const [isModalOpen, setIsModalOpen] = useState(false);
    const [diagnoses, setDiagnoses] = useState([]);
    const [assurances, setAssurances] = useState([]);
    const [operations, setOperations] = useState([]);
    const [doctors, setDoctors] = useState([]);
    const [form, setForm] = useState({
        amount: 0,
        assurance_id: '',
        diagnosis_id: '',
        operation_id: 0,
        doctor_id: 0
    });

    const handleChange =
        (value) => (e) => {
        console.log(e.target.value)
        setForm((prevForm) => ({
            ...prevForm,
            [value]: e.target.value,
        }));
    };

    const handleErrorClose = () => {
        return setIsErrorVisible(false)
    }

    function constructUrl() {
        return `http://localhost:8080/api/v1/med-ass/refund/search?offset=${offset}&limit=${limit}`;
    }

    function getSpanClass(refundStatus) {
        switch (refundStatus) {
            case "AWAITING":
                return "yellow-circle"
            case "CANCELED":
                return "red-circle"
            case "PAID":
                return "green-circle"
        }
    }

    const handleAddRefundClick = () => {
        fetchDiagnoses()
        fetchOperations()
        fetchAssurances()
        setIsModalOpen(true);
    };

    const handleCloseModal = () => {
        setIsModalOpen(false);
    };

    const handleModalSubmit = async (data) => {
        data.preventDefault();

        const newRefund = await fetchWrapper('http://localhost:8080/api/v1/med-ass/refund', {
            method: 'POST',
            body: JSON.stringify(form),
            headers: {
                "Accept": "application/json",
                "Content-Type": "application/json",
            },
        });
        if (!newRefund) {
            setIsErrorVisible(true);
        } else {
            console.log(newRefund)
            refunds.push(newRefund)
        }
        handleCloseModal();
    };

    const fetchDiagnoses = async () => {
        const data = await fetchWrapper('http://localhost:8080/api/v1/med-ass/diagnosis')
        if (data) {
            setDiagnoses(data)
        }
    }

    const fetchOperations = async () => {
        const data = await fetchWrapper('http://localhost:8080/api/v1/med-ass/operations')
        if (data) {
            setOperations(data)
        }
    }

    const fetchAssurances = async () => {
        const data = await fetchWrapper('http://localhost:8080/api/v1/med-ass/assurance/search?active=true')
        if (data) {
            setAssurances(data)
        }
    }

    const fetchDoctorsForOperation = async () => {
        const data = await fetchWrapper(`http://localhost:8080/api/v1/med-ass/doctors/search?operationId=${form.operation_id}`)
        console.log(data)
        if (data) {
            setDoctors(data)
        }
    }

    const fetchRefunds = async () => {
        const data = await fetchWrapper(constructUrl())
        if(!data){
            setIsErrorVisible(true)
        }
        if(!data.refunds) {
            setIsErrorVisible(true)
        }
        setRefunds(data.refunds)
        setTotalPages(data.total_pages);
    }

    useEffect(() => {
        fetchDoctorsForOperation()
    }, [form.operation_id]);

    useEffect(() => {
        if (!isModalOpen) {
            fetchRefunds()
        }
    }, [refunds.length, offset])
    return (
        <div className="refunds-page">
            <main className="main-content">
                <Header/>
                <div className="add-refund">
                    <button className="add-refund-btn" onClick={handleAddRefundClick}>+ Request a refund</button>
                </div>
                <table className="refunds-table">
                    <thead>
                    <tr>
                        <th>Refund status</th>
                        <th>Assurance type</th>
                        <th>Diagnosis name</th>
                        <th>Doctor name</th>
                        <th>Operation name</th>
                        <th>Amount</th>
                        <th>Last update</th>
                    </tr>
                    </thead>
                    <tbody>
                    {refunds.length > 0 ? (
                        refunds.map((refund, index) => (
                            <tr key={index}>
                                <td>
                                    <span className={getSpanClass(refund.refund_status)}></span>
                                    <a>{refund.refund_status}</a>
                                </td>
                                <td>{refund.assurance_type}</td>
                                <td>{refund.diagnosis_name}</td>
                                <td>{refund.doctor_name}</td>
                                <td>{refund.operation_name}</td>
                                <td>{refund.amount} €</td>
                                <td>{refund.updated_at}</td>
                            </tr>
                        ))
                    ) : (
                        <tr>
                            <td colSpan="5">No refunds found</td>
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
                        <button className="close-button" onClick={handleCloseModal}>×</button>
                        <h2>Request a refund</h2>
                        <form onSubmit={handleModalSubmit}>
                            <label className="modal-label">Select a diagnose:</label>
                            <select className="modal-input" value={form.diagnosis_id} onChange={handleChange("diagnosis_id")}>
                                {diagnoses.map((diagnose, index) => (
                                    <option key={index} value={diagnose.id}>
                                        {diagnose.name}
                                    </option>
                                ))}
                            </select>

                            <label className="modal-label">Select operation:</label>
                            <select className="modal-input" value={form.operation_id} onChange={handleChange("operation_id")}>
                                {operations.map((operation, index) => (
                                    <option key={index} value={operation.id}>
                                        {operation.name}, {operation.id}
                                    </option>
                                ))}
                            </select>

                            {(form.diagnosis_id !== '' && form.operation_id !== 0 && isModalOpen) && (
                                <>
                                    <label className="modal-label">Select an assurance:</label>
                                    <select className="modal-input" value={form.assurance_id} onChange={handleChange("assurance_id")}>
                                        {assurances.map((assurance, index) => (
                                            <option key={index} value={assurance.id}>
                                                {assurance.assurance_type}
                                            </option>
                                        ))}
                                    </select>

                                    <label className="modal-label">Select a doctor:</label>
                                    <select className="modal-input" value={form.doctor_id} onChange={handleChange("doctor_id")}>
                                        {doctors.map((doctor, index) => (
                                            <option key={index} value={doctor.id}>
                                                {doctor.name}
                                            </option>
                                        ))}
                                    </select>

                                    <label className="modal-label">Enter an amount to refund:</label>
                                    <div className="modal-input">
                                        <input type="number" value={form.amount} onChange={handleChange("amount")} />
                                    </div>
                                </>
                            )}
                            <button className="submit-button" type="submit">Submit</button>
                        </form>

                    </div>
                </div>
            )}
            {isErrorVisible && (
            <FloatingErrorMessage
                message="An error occurred! Please try again."
                onClose={handleErrorClose}
            />
        )}
        </div>
    );
};

export default RefundsPage;