import React, { createContext, useContext, useState } from 'react';

const AuthContext = createContext();

export function useAuth() {
    return useContext(AuthContext);
}

export const AuthProvider = ({ children }) => {
    const [isLoggedIn, setIsLoggedIn] = useState(false);

// AuthProvider 내 login 함수 수정
    const login = async (accessToken) => {
        if (accessToken) {
            await localStorage.setItem("accessToken", accessToken);
            setIsLoggedIn(true);
            // 추가적인 사용자 정보 설정 로직이 필요한 경우 여기에 포함
        }
    };


    const logout = () => {
        setIsLoggedIn(false);
    };

    const value = {
        isLoggedIn,
        login,
        logout,
    };

    return (
        <AuthContext.Provider value={value}>
            {children}
        </AuthContext.Provider>
    );
};
