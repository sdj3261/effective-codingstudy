import { Navigate, Outlet } from 'react-router-dom';
import { useAuth } from '../pages/auth/AuthContext'; // useAuth 훅의 경로를 확인하세요.

export const PrivateLayout = () => {
    const { isLoggedIn } = useAuth(); // 인증 상태를 확인하는 로직

    return isLoggedIn ? <Outlet /> : <Navigate to="/" replace />;
};
