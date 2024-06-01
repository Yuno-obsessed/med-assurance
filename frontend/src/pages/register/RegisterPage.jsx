import React, {useState} from 'react';
import { useForm } from 'react-hook-form';
import styled from 'styled-components';
import {useNavigate} from "react-router-dom";
import FloatingErrorMessage from "../../component/ErrorMessage";

const Container = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
`;

const Form = styled.form`
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 1rem;
  width: 60%;
  background: #f9f9f9;
  padding: 2rem;
  border-radius: 8px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
`;

const FormControl = styled.div`
  display: flex;
  flex-direction: column;
`;

const Label = styled.label`
  margin-bottom: 0.5rem;
`;

const Input = styled.input`
  padding: 0.5rem;
  border: 1px solid #ccc;
  border-radius: 4px;
`;

const Select = styled.select`
  padding: 0.5rem;
  border: 1px solid #ccc;
  border-radius: 4px;
`;

const SubmitButton = styled.button`
  grid-column: span 2;
  padding: 1rem;
  background: #007bff;
  color: #fff;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  text-align: center;
`;

const Error = styled.span`
  color: red;
  font-size: 0.875rem;
`;

const RegisterPage = () => {
    const { register, handleSubmit, formState: { errors } } = useForm();
    const navigate = useNavigate();
    const [isErrorVisible, setIsErrorVisible] = useState(false);
    const [errorMessage, setErrorMessage] = useState('');

    const handleErrorClose = () => {
        setIsErrorVisible(false);
    };

    const requestRegister = async data => {
        const response = await fetch("http://localhost:8080/api/v1/med-ass/register", {
            method: 'POST',
            body: JSON.stringify({
                first_name: data.firstName,
                last_name: data.lastName,
                age: data.age,
                passport_number: data.passportNumber,
                password: data.password,
                email: data.email,
                address: data.address,
                income_type: data.incomeType,
                children: data.children,
                iban: data.iban,
            }),
            headers: {
                'Content-Type': 'application/json'
            },
        })
        if (response.ok) {
            const data = await response.json();
            return await data
        } else {
            const data = await response.json();
            setIsErrorVisible(true);
            setErrorMessage(data.message);
        }
    }

    const onSubmit = (data) => {
        console.log(data);
        requestRegister(data).then((response) => {
            if (response) {
                navigate('/login')
            }
        })
    };

    return (
        <main>
        <Container>
            <Form onSubmit={handleSubmit(onSubmit)} autoComplete="off">
                <FormControl>
                    <Label>First Name:</Label>
                    <Input {...register('firstName', { required: 'First name is required' })} />
                    {errors.firstName && <Error>{errors.firstName.message}</Error>}
                </FormControl>

                <FormControl>
                    <Label>Last Name:</Label>
                    <Input {...register('lastName', { required: 'Last name is required' })} />
                    {errors.lastName && <Error>{errors.lastName.message}</Error>}
                </FormControl>

                <FormControl>
                    <Label>Age:</Label>
                    <Input type="number" {...register('age', { required: 'Age is required' })} />
                    {errors.age && <Error>{errors.age.message}</Error>}
                </FormControl>

                <FormControl>
                    <Label>Passport Number:</Label>
                    <Input {...register('passportNumber', { required: 'Passport number is required' })} />
                    {errors.passportNumber && <Error>{errors.passportNumber.message}</Error>}
                </FormControl>

                <FormControl>
                    <Label>Email:</Label>
                    <Input type="email" {...register('email', { required: 'Email is required' })} />
                    {errors.email && <Error>{errors.email.message}</Error>}
                </FormControl>

                <FormControl>
                    <Label>Password:</Label>
                    <Input type="password" {...register('password', { required: 'Password is required' })} />
                    {errors.password && <Error>{errors.password.message}</Error>}
                </FormControl>

                <FormControl>
                    <Label>Address:</Label>
                    <Input {...register('address', { required: 'Address is required' })} />
                    {errors.address && <Error>{errors.address.message}</Error>}
                </FormControl>

                <FormControl>
                    <Label>Income Type:</Label>
                    <Select {...register('incomeType', { required: 'Address is required' })} >
                        <option value="LOW">Low (600-1400$ per month)</option>
                        <option value="MEDIUM">Medium (1400- 2400$ per month)</option>
                        <option value="HIGH">High (2400+$ per month)</option>
                    </Select>
                </FormControl>

                <FormControl>
                    <Label>Children:</Label>
                    <Input type="number" {...register('children', { required: 'Number of children is required' })} />
                    {errors.children && <Error>{errors.children.message}</Error>}
                </FormControl>

                <FormControl>
                    <Label>IBAN:</Label>
                    <Input
                        {...register('iban', {
                            required: 'IBAN is required',
                            pattern: {
                                value: /^[A-Z]{2}[0-9A-Z]{13,32}$/,
                                message: 'Invalid IBAN format'
                            }
                        })}
                    />
                    {errors.iban && <Error>{errors.iban.message}</Error>}
                </FormControl>

                <SubmitButton type="submit">Register</SubmitButton>
            </Form>
        </Container>
            {isErrorVisible && (
                <FloatingErrorMessage
                    message={errorMessage}
                    onClose={handleErrorClose}
                />
            )}
        </main>
    );
};

export default RegisterPage;