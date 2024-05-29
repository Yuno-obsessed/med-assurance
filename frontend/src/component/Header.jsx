import React, { useEffect, useState } from 'react';
import {useUserStore} from "../store/user";

import styled from 'styled-components';
import {useAuthStore} from "../store/auth";

const HeaderContainer = styled.header`
    margin-bottom: 20px;
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 10px 20px;
    background-color: #f8f9fa;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
`;

const Title = styled.div`
    font-size: 1.5rem;
    font-weight: bold;
`;

const UserInfo = styled.div`
    display: flex;
    align-items: center;
`;

const UserImage = styled.img`
    width: 40px;
    height: 40px;
    border-radius: 50%;
    margin-right: 10px;
`;

const UserData = styled.div`
    display: flex;
    flex-direction: column;
`;

const UserName = styled.div`
    font-size: 1rem;
    font-weight: bold;
`;

const UserEmail = styled.div`
    font-size: 0.875rem;
    color: #6c757d;
`;

const LoadingMessage = styled.div`
    font-size: 1rem;
    color: #6c757d;
`;

const Header = () => {
    const {userData, setUserData} = useUserStore();
    const {removeToken} = useAuthStore();

    return (
        <HeaderContainer>
            <Title>My Situation</Title>
            {userData ? (
                <UserInfo>
                    <UserImage src={userData.image} alt="User" />
                    <UserData>
                        <UserName>{userData.name}</UserName>
                        <UserEmail>{userData.email}</UserEmail>
                    </UserData>
                    <button onClick={() => {
                        removeToken()
                        window.location.reload()
                    }}>LOGOUT</button>
                </UserInfo>
            ) : (
                <LoadingMessage>Loading user data...</LoadingMessage>
            )}
        </HeaderContainer>
    );
};

export default Header;