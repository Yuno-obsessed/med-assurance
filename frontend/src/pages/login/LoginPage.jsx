import React, {useEffect, useState} from 'react';
import { useNavigate } from 'react-router-dom';
import styled from 'styled-components';
import fetchWrapper from "../../api/middleware/auth";
import {useUserStore} from "../../store/user";
import FloatingErrorMessage from "../../component/ErrorMessage";
import {useAuthStore} from "../../store/auth";

const PageContainer = styled.div`
    display: flex;
    justify-content: center;
    align-items: center;
    height: 100vh;
    bfackground: #f9f9f9;
`;

const LoginContainer = styled.div`
    background: #fff;
    padding: 40px;
    border-radius: 10px;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
    max-width: 400px;
    width: 100%;
`;

const Title = styled.h2`
    text-align: center;
    margin-bottom: 20px;
    color: #333;
`;

const FormGroup = styled.div`
    margin-bottom: 20px;
`;

const Label = styled.label`
    display: block;
    margin-bottom: 8px;
    color: #555;
`;

const Input = styled.input`
    width: 100%;
    padding: 10px;
    border: 1px solid #ddd;
    border-radius: 5px;
    outline: none;
    &:focus {
        border-color: #6e8efb;
    }
`;

const SubmitButton = styled.button`
    width: 100%;
    padding: 10px;
    border: none;
    border-radius: 5px;
    background: #6e8efb;
    color: #fff;
    font-size: 16px;
    cursor: pointer;
    transition: background 0.3s;
    &:hover {
        background: #a777e3;
    }
`;

const LoginPage = () => {
    const {setIsAuth, login , token} = useAuthStore()
    const {getUserData} = useUserStore()
    const navigate = useNavigate();
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [isErrorVisible, setIsErrorVisible] = useState(false);
    const [errorMessage, setErrorMessage] = useState('');

    const handleErrorClose = () => {
        setIsErrorVisible(false);
    };

    useEffect(() => {
        checkUser()
        setEmail('');
        setPassword('');
    }, []);

    const checkUser = async () => {
        getUserData().then((response) => {
            if (response) {
                setIsAuth();
                navigate('/')
            }
        })
    }

    const handleLogin = async (e) => {
        e.preventDefault();
        login(email, password).then((response) => {
            if (response.error) {
                setErrorMessage(response.error)
                setIsErrorVisible(true);
            } else {
                checkUser();
            }
        })
    };
    return (
        <PageContainer>
            <LoginContainer>
                <Title>Login</Title>
                <form onSubmit={handleLogin} autoComplete="off">
                    <FormGroup>
                        <Label>Email:</Label>
                        <Input
                            type="email"
                            value={email}
                            onChange={(e) => setEmail(e.target.value)}
                            required
                        />
                    </FormGroup>
                    <FormGroup>
                        <Label>Password:</Label>
                        <Input
                            type="password"
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                            required
                        />
                    </FormGroup>
                    <SubmitButton type="submit">Login</SubmitButton>
                </form>
            </LoginContainer>
            {isErrorVisible && (
                <FloatingErrorMessage
                    message={errorMessage}
                    onClose={handleErrorClose}
                />
            )}
        </PageContainer>
    );
};

export default LoginPage;