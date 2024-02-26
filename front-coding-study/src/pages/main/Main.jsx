import Login from "../auth/Login";
import {useNavigate} from "react-router-dom";
import authAxios from "../../interceptors";
import {useEffect, useState} from "react";

const Main = () => {
    const [loginUser, setLoginUser] = useState({});
    const navigate = useNavigate();

    // auth 필요한 API 테스트
    const componentDidMount = async () => {
        const res = (await authAxios.get("/auth/test")).data;
        console.log(res)
        setLoginUser(() => res);
    }

    useEffect(() => {componentDidMount()
    }, []);

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
            <div>username: {loginUser.username}</div>
            <div>name: {loginUser.name}</div>
            <br />
            <button onClick={logout}>로그아웃</button>
        </>
    )
}

export default Main;