import React from 'react';
import { BrowserRouter as Router, Route, Routes, Navigate } from 'react-router-dom';
import {useAuth} from './layout/AuthContext'
import { Layout, Menu, Breadcrumb, Card } from 'antd';
import {
    DesktopOutlined,
    PieChartOutlined,
    FileOutlined,
    ConsoleSqlOutlined,
    LinkOutlined,
    UserOutlined,
    NotificationOutlined,
    MailOutlined
} from '@ant-design/icons';
import Login from "./Login";
import Main from "./Main";
import GetToken from "./GetToken";
import NotFound from "./layout/NotFound";
import AppHeader from './layout/AppHeader'


const { Header, Content, Footer, Sider } = Layout;
const { SubMenu } = Menu;
// PrivateRoute 컴포넌트는 로그인 상태에 따라 접근을 제어합니다.
function PrivateRoute({ children }) {
    const isLoggedIn = useAuth(); // useAuth 훅으로 로그인 상태를 확인합니다.
    return isLoggedIn ? children : <Navigate to="/" />;
}

const App = () => (
    <Router>
        <Layout style={{ minHeight: '100vh' }}>
            <Sider collapsible>
                <div className="logo" />
                <Menu theme="dark" defaultSelectedKeys={['1']} mode="inline">
                    <Menu.Item key="1" icon={<PieChartOutlined />}>
                        Dashboard
                    </Menu.Item>
                    <Menu.Item key="2" icon={<DesktopOutlined />}>
                        Algorithm
                    </Menu.Item>
                    <Menu.Item key="3" icon={<ConsoleSqlOutlined />}>
                        SQL
                    </Menu.Item>
                    <Menu.Item key="4" icon={<UserOutlined />}>
                        GuestBook
                    </Menu.Item>
                    <Menu.Item key="5" icon={<MailOutlined/>}>
                        Contact
                    </Menu.Item>
                    <Menu.Item key="6" icon={<NotificationOutlined/>}>
                        Notification
                    </Menu.Item>
                    <Menu.Item key="7" icon={<FileOutlined />}>
                        File
                    </Menu.Item>
                    <Menu.Item key="8" icon={<LinkOutlined />}>
                        Notion
                    </Menu.Item>
                    <Menu.Item key="9" icon={<LinkOutlined />}>
                        Programmers
                    </Menu.Item>
                    <Menu.Item key="10" icon={<LinkOutlined />}>
                        Baekjoon
                    </Menu.Item>
                </Menu>
            </Sider>
            <Layout className="site-layout">
                <AppHeader  />
                <Content style={{ margin: '0 16px' }}>
                    <Breadcrumb style={{ margin: '16px 0' }}>
                        <Breadcrumb.Item>User</Breadcrumb.Item>
                        <Breadcrumb.Item>Bill</Breadcrumb.Item>
                    </Breadcrumb>
                    <div className="site-layout-background" style={{ padding: 24, minHeight: 360 }}>
                        {/* 컨텐츠 라우팅 */}
                        <Routes>
                            <Route path="/" element={<Login />} />
                            <Route path="/oauth/:accessToken" element={<GetToken />} />
                            <Route path="/main" element={<PrivateRoute><Main /></PrivateRoute>} />
                            <Route path="*" element={<NotFound />} />
                        </Routes>
                    </div>
                </Content>
                <Footer style={{ textAlign: 'center' }}>
                    T DoDream. ©2024. Created by Ant UED
                </Footer>
            </Layout>
        </Layout>
    </Router>
);

export default App;
