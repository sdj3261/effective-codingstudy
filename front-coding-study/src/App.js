import React, { Suspense } from "react";
import { useAuth } from "./layout/AuthContext"; // 이 경로가 정확한지 확인하세요.
import { BrowserRouter, Route, Routes, Navigate } from "react-router-dom";
import Login from "./Login";
import Main from "./Main";
import GetToken from "./GetToken";
import { AuthProvider } from "./layout/AuthContext"; // 경로가 올바른지 확인하세요
import NotFound from "./layout/NotFound";
import Header from "./layout/Header";
import Sidebar from "./layout/Sidebar";
import Footer from "./layout/Footer";

function PrivateRoute({ children }) {
    const { isLoggedIn } = useAuth(); // 로그인 상태 확인
    return isLoggedIn ? children : <Navigate to="/" />;
}

function App() {
    return (
        <AuthProvider> {/* AuthProvider로 App 컴포넌트 감싸기 */}
            <BrowserRouter>
                <Suspense fallback="..loading">
                    <div className="App">
                        <Header />
                        <Sidebar />
                        <main className="App-content">
                            <Routes>
                                <Route path="/" element={<Login/>}/>
                                <Route path="/oauth/:accessToken" element={<GetToken/>}/>
                                <Route path="/main" element={<PrivateRoute><Main/></PrivateRoute>}/>
                                <Route path="*" element={<NotFound/>}/>
                            </Routes>
                        </main>
                        <Footer />
                    </div>
                </Suspense>
            </BrowserRouter>
        </AuthProvider>
    );
}

export default App;
