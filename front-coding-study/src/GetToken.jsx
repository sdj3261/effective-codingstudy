import React, { useEffect } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { useAuth } from "./layout/AuthContext"; // 경로는 실제 경로에 맞게 조정해주세요.

const GetToken = () => {
    let { accessToken } = useParams();
    const navigate = useNavigate();
    const { login } = useAuth(); // useAuth 훅을 통해 login 함수에 접근

    useEffect(() => {
        if (accessToken) {
            login(accessToken).then(() => navigate("/main", { replace: true }));
        }
    }, [accessToken, login, navigate]);

    return null; // 렌더링할 내용이 없는 경우 null 반환
};

export default GetToken;
