import React, { useEffect, useState } from 'react';
import {useUserStore} from "../store/user";

import styled from 'styled-components';
import {useAuthStore} from "../store/auth";
import {useLocation, useNavigate} from "react-router-dom";

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

const LogoutImage = styled.img`
    width: 25px;
    height: 25px;
    margin-right: 10px;
  padding-left: 10px;
  cursor:pointer;
`;

const LogoutDiv = styled.div`
    width: 40px;
    height: 40px;
    border-radius: 50%;
    margin-right: 10px;
  display: flex;
  align-items: center;
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
    const location = useLocation();

    return (
        <HeaderContainer>
            { location.pathname.startsWith("/doctor") ? (
                <Title>Doctors</Title>
            ) : (
                <Title>My Situation</Title>
            )}
            {userData ? (
                <UserInfo>
                    <UserImage src="https://th.bing.com/th/id/OIP.ItvA9eX1ZIYT8NHePqeuCgAAAA?rs=1&pid=ImgDetMain" alt="" />
                    <UserData>
                        <UserName>{userData.first_name}</UserName>
                        <UserEmail>{userData.email}</UserEmail>
                    </UserData>
                    <div>
                        <LogoutDiv onClick={() => {
                            removeToken()
                            window.location.reload()
                        }}>
                            <LogoutImage src="https://static.vecteezy.com/system/resources/previews/020/839/751/original/logout-icon-for-web-ui-design-vector.jpg"/>
                        </LogoutDiv>
                    </div>
                </UserInfo>
            ) : (
                <LoadingMessage>Loading user data...</LoadingMessage>
            )}
        </HeaderContainer>
    );
};

export default Header;