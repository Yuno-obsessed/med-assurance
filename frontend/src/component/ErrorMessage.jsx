import React, { useState } from 'react';
import styled from 'styled-components';

const ErrorMessageContainer = styled.div`
    position: fixed;
    top: 20px;
    right: 20px;
    background-color: #f8d7da;
    color: #721c24;
    padding: 15px 20px;
    border: 1px solid #f5c6cb;
    border-radius: 5px;
    box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
    z-index: 1000;
    display: flex;
    justify-content: space-between;
    align-items: center;
    min-width: 300px;
`;

const CloseButton = styled.button`
    background: none;
    border: none;
    font-size: 20px;
    font-weight: bold;
    color: #721c24;
    cursor: pointer;
    outline: none;
`;

const ErrorMessageText = styled.div`
    flex-grow: 1;
    padding-right: 10px;
`;

const FloatingErrorMessage = ({ message, onClose }) => {
    return (
        <ErrorMessageContainer>
            <ErrorMessageText>{message}</ErrorMessageText>
            <CloseButton onClick={onClose}>&times;</CloseButton>
        </ErrorMessageContainer>
    );
};

export default FloatingErrorMessage;