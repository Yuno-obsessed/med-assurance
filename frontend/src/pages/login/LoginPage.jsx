import React, {useEffect, useState} from 'react';
import { useNavigate } from 'react-router-dom';
import styled from 'styled-components';
import fetchWrapper from "../../api/middleware/auth";
import useUserStore from "../../store";
import FloatingErrorMessage from "../../component/ErrorMessage";

const PageContainer = styled.div`
    display: flex;
    justify-content: center;
    align-items: center;
    height: 100vh;
    background: linear-gradient(135deg, #6e8efb, #a777e3);
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
    const {userData, setUserData} = useUserStore();
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const navigate = useNavigate();
    useEffect(() => {
        setEmail('');
        setPassword('');
    }, []);
    const [isErrorVisible, setIsErrorVisible] = useState(false);

    const handleErrorClose = () => {
        setIsErrorVisible(false);
    };

    const handleLogin = async (e) => {
        e.preventDefault();

        await fetch('http://localhost:8080/api/v1/med-ass/login', {
            method: 'POST',
            body: JSON.stringify({
                email: email,
                password: password
            }),
            headers: {
                "Accept": "application/json",
                "Content-Type": "application/json",
            },
        }).then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok ' + response.statusText);
            }
            return response.json();
        }).then(data => {
            console.log(data);
            const err = data.error;
            switch (err) {
                case "WRONG_PASSWORD":
                    alert("Wrong password");
                    break;
                case "NO_SUCH_USER":
                    alert("No user with such email found");
                    break;
            }
            const token = data.access_token;
            localStorage.setItem('access_token', token);
            const fetchUserData = async () => {
                try {
                    if (token) {
                        const data = await fetchWrapper('http://localhost:8080/api/v1/med-ass/access', {
                            method: 'GET',
                        });
                        if (data) {
                            console.log(data);
                            setUserData(data);
                        } else {
                            console.error('Failed to fetch user data');
                        }
                    }
                } catch (error) {
                    console.error('Error fetching user data:', error);
                }
            };
            setUserData(fetchUserData());
            navigate('/');
        }).catch(error => {
            console.error('There was a problem with the fetch operation:', error);
        });

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
                    message="An error occurred! Please try again."
                    onClose={handleErrorClose}
                />
            )}
        </PageContainer>
    );
};

export default LoginPage;