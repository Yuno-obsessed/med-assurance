import React, {useEffect, useState} from 'react';
import './AssurancesPage.css';
import Header from "../../component/Header";
import fetchWrapper from "../../api/middleware/auth";
import FloatingErrorMessage from "../../component/ErrorMessage";
import {Link} from "react-router-dom";
import moment from "moment";


const AssurancesPage = () => {
    const [assuranceType, setAssuranceType] = useState('');
    const [durationType, setDurationType] = useState('');
    const [price, setPrice] = useState('');
    const [activeUntil, setActiveUntil] = useState('');
    const [assurances, setAssurances] = useState([]);
    const [isModalOpen, setIsModalOpen] = useState(false);

    useEffect(() => {
        const fetchAssurances = async () => {
            try {
                const data = await fetchWrapper('http://localhost:8080/api/v1/med-ass/assurance/search?active=false');
                data.sort(function(a, b) {
                    return moment(a.active_until, "YYYY-MM-DD hh:mm").isAfter(moment(b.active_until, "YYYY-MM-DD hh:mm")) ? -1 : 1;
                });
                setAssurances(data);
            } catch (error) {
                console.error('Error fetching assurances:', error);
            }
        };
        fetchAssurances();
    }, [assurances.length]);

    useEffect(() => {
        if (assuranceType && durationType) {
            const fetchAssuranceDetails = async () => {
                try {
                    const data = await fetchWrapper(`http://localhost:8080/api/v1/med-ass/assurance?assuranceType=${assuranceType}&durationType=${durationType}`);
                    console.log(data);
                    setPrice(data.price);
                    setActiveUntil(data.active_until);
                } catch (error) {
                    console.error('Error fetching assurance details:', error);
                }
            };
            fetchAssuranceDetails();
        }
    }, [assuranceType, durationType]);

    const handleAddAssuranceClick = () => {
        setIsModalOpen(true);
    };

    const handleCloseModal = () => {
        setIsModalOpen(false);
        setActiveUntil('');
        setPrice('')
    };

    const isActive = (activeUntil) => {
        const activeDate = moment(activeUntil, "YYYY-MM-DD hh:mm");
        if (activeDate.isValid()) {
            return activeDate.isAfter(moment())
        } else {
            return false;
        }
    }

    const [isErrorVisible, setIsErrorVisible] = useState(false);

    const handleErrorClose = () => {
        setIsErrorVisible(false);
    };

    const handleModalSubmit = async (data) => {
        data.preventDefault();

        const response = await fetchWrapper('http://localhost:8080/api/v1/med-ass/assurance', {
            method: 'POST',
            body: JSON.stringify({
                assurance_type: assuranceType,
                duration_type: durationType,
                price: price,
                active_until: activeUntil
            }),
            headers: {
                "Accept": "application/json",
                "Content-Type": "application/json",
            },
        });
        if (!response) {
            setIsErrorVisible(true);
        } else {
            console.log(response);
            assurances.push(response);
        }
        handleCloseModal();
    };

    return (
        <div className="assurance-page">

            <main className="main-content">
                <Header></Header>
                <div className="filters">
                    <button className="add-assurance-btn" onClick={handleAddAssuranceClick}>+ Add an assurance</button>
                    {/*<div className="filter-inputs">*/}
                    {/*    <input type="text" placeholder="Фільтри" />*/}
                    {/*</div>*/}
                </div>
                <table className="assurance-table">
                    <thead>
                    <tr>
                        <th>Active until</th>
                        <th>Assurance type</th>
                        <th>Duration type</th>
                        <th>Percent</th>
                    </tr>
                    </thead>
                    <tbody>
                    {assurances.length > 0 ? (
                        assurances.map((assurance, index) => (
                            isActive(assurance.active_until) ? (
                                <tr key={index}>
                                    <td>{assurance.active_until}</td>
                                    <td>{assurance.assurance_type}</td>
                                    <td>{assurance.duration_type}</td>
                                    <td>{assurance.percent}</td>
                                </tr>
                            ) : (
                                <tr key={index} style={{ textDecoration: "line-through", color: "darkgray" }}>
                                    <td>{assurance.active_until}</td>
                                    <td>{assurance.assurance_type}</td>
                                    <td>{assurance.duration_type}</td>
                                    <td>{assurance.percent}</td>
                                </tr>
                            )
                        ))
                    ) : (
                        <tr>
                            <td colSpan="4">No assurances found</td>
                        </tr>
                    )}
                    </tbody>
                </table>
            </main>
            {isModalOpen && (
                <div className="modal-overlay">
                    <div className="modal">
                        <button className="close-button" onClick={handleCloseModal}>×</button>
                        <h2>Add assurance</h2>
                        <form onSubmit={handleModalSubmit}>
                            <select className="modal-input" required onChange={(e) => setAssuranceType(e.target.value)}>
                                <option value="">Select Assurance Type</option>
                                <option value="MANDATORY">Mandatory</option>
                                <option value="VOLUNTARY">Voluntary</option>
                                <option value="CRITICAL">Critical</option>
                            </select>

                            <select className="modal-input" required onChange={(e) => setDurationType(e.target.value)}>>
                                <option value="">Select Duration Type</option>
                                <option value="MONTHLY">Monthly</option>
                                <option value="SEMI_ANNUAL">Semi Annual</option>
                                <option value="ANNUAL">Annual</option>
                            </select>

                            {(assuranceType !== '' && durationType !== '') && (
                                <>
                                    <div className="modal-input">
                                        <label>Price:</label>
                                        <input type="text" value={price} readOnly />
                                    </div>
                                    <div className="modal-input">
                                        <label>Active Until:</label>
                                        <input type="text" value={activeUntil} readOnly />
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

export default AssurancesPage;