import React from 'react';
import { useForm } from 'react-hook-form';
import 'react-datepicker/dist/react-datepicker.css';
import styled from 'styled-components';
import fetchWrapper from "../../api/middleware/auth";

const Container = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
`;

const Form = styled.form`
  display: flex;
  flex-direction: column;
  gap: 15px;
  padding: 20px;
  border: 1px solid #ccc;
  border-radius: 10px;
  box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
  background-color: #f9f9f9;
  width: 300px;
`;

const FormControl = styled.div`
  display: flex;
  flex-direction: column;
  gap: 5px;
`;

const Label = styled.label`
  font-weight: bold;
`;

const Input = styled.input`
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 5px;
`;

const Error = styled.span`
  color: red;
  font-size: 12px;
`;

const SubmitButton = styled.button`
  padding: 10px;
  border: none;
  border-radius: 5px;
  background-color: #007bff;
  color: white;
  cursor: pointer;

  &:hover {
    background-color: #0056b3;
  }
`;

const CreateAssurancePage = () => {
    const { register, handleSubmit, formState: { errors }, setValue } = useForm();
    const [startDate, setStartDate] = React.useState(null);

    const onSubmit = data => {
        const response = fetchWrapper('http://localhost:8080/api/v1/med-ass/assurance', {
            method: 'POST',
            body: JSON.stringify({
                user_id: data.userID,
                assurance_type: data.assuranceType,
                duration_type: data.durationType,
            }),
            headers: {
                "Accept": "application/json",
                "Content-Type": "application/json",
            },
        });
        if (!response.ok) {
            throw new Error('Network response was not ok ' + response.statusText);
        } else {
            return response.json();

        }
    };

    return (
        <Container>
            <Form onSubmit={handleSubmit(onSubmit)}>
                <FormControl>
                    <Label>User id:</Label>
                    <Input
                        {...register('userID', {
                            required: 'This field is required',
                            maxLength: { value: 36, message: 'Maximum length is 36 characters' }
                        })}
                    />
                    {errors.userID && <Error>{errors.userID.message}</Error>}
                </FormControl>

                <FormControl>
                    <Label>Assurance Type:</Label>
                    <select
                        {...register('assuranceType', { required: 'This field is required' })}
                    >
                        <option value="">Select Assurance Type</option>
                        <option value="MANDATORY">Mandatory</option>
                        <option value="VOLUNTARY">Voluntary</option>
                        <option value="CRITICAL">Critical</option>
                    </select>
                    {errors.assuranceType && <Error>{errors.assuranceType.message}</Error>}
                </FormControl>

                <FormControl>
                    <Label>Duration Type:</Label>
                    <select
                        {...register('durationType', { required: 'This field is required' })}
                    >
                        <option value="">Select Duration Type</option>
                        <option value="MONTHLY">Monthly</option>
                        <option value="SEMI_ANNUAL">Semi Annual</option>
                        <option value="ANNUAL">Annual</option>
                    </select>
                    {errors.durationType && <Error>{errors.durationType.message}</Error>}
                </FormControl>

                <SubmitButton type="submit">Submit</SubmitButton>
            </Form>
        </Container>
    );
};

export default CreateAssurancePage;