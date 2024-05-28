import React from 'react';
import { useForm } from 'react-hook-form';
import styled from 'styled-components';

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

    const onSubmit = data => {
        console.log(data);
    };

    return (
        <Container>
            <Form onSubmit={handleSubmit(onSubmit)}>
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
                    <Label>Address:</Label>
                    <Input {...register('address', { required: 'Address is required' })} />
                    {errors.address && <Error>{errors.address.message}</Error>}
                </FormControl>

                <FormControl>
                    <Label>Income Type:</Label>
                    <Input {...register('incomeType', { required: 'Income type is required' })} />
                    {errors.incomeType && <Error>{errors.incomeType.message}</Error>}
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
    );
};

export default RegisterPage;