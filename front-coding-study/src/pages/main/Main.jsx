import Login from "../auth/Login";
import {useNavigate} from "react-router-dom";
import authAxios from "../../interceptors";
import errorPage from "../error/ErrorPage";
import {useEffect, useState} from "react";

const Main = () => {
    const [loginUser, setLoginUser] = useState({});
    const navigate = useNavigate();

    useEffect(() => {
        const fetchAuthData = async () => {
            try {
                const response = await authAxios.get("/auth/test");
                const res = response.data;

                // res.success가 true일 경우, 로그인 사용자 데이터를 설정
                if (res.success) {
                    console.log(res);
                    setLoginUser(() => res.data);
                } else {
                    // res.success가 false인 경우, 에러 페이지로 이동
                    navigate('/errorPage'); // '/errorPage'는 ErrorPage 컴포넌트로 라우팅된 경로 가정
                }
            } catch (error) {
                console.error("API Request Error :", error);
                // 네트워크 오류 등의 예외 상황 처리, 에러 페이지로 이동
                navigate('/errorPage'); // '/errorPage'는 ErrorPage 컴포넌트로 라우팅된 경로 가정
            }
        };

        fetchAuthData()
    }, [navigate]);
    const logout = () => {
        logLocalStorageData()
        localStorage.clear()
        navigate("/");
    }

    const logLocalStorageData = () => {
        for (let i = 0; i < localStorage.length; i++) {
            const key = localStorage.key(i);
            const value = localStorage.getItem(key);
            console.log(`${key}: ${value}`);
        }
    }

// 함수 호출하여 로컬 스토리지의 모든 데이터 로그 찍기
    logLocalStorageData();


    return (
        <>
            <h1>메인 페이지</h1>
            <div>로그인 유저 정보</div>
            <div>username: {loginUser.email}</div>
            <div>name: {loginUser.name}</div>
            <div>name: {loginUser.toString()}</div>
            <br/>
            <button onClick={logout}>로그아웃</button>
        </>
    )
}

export default Main;