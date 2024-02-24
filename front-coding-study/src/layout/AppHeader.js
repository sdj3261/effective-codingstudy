import React from 'react';
import { Layout, Menu, Input, Button } from 'antd';
import { SearchOutlined, UserOutlined, UnlockOutlined } from '@ant-design/icons';

const { Header } = Layout;

const AppHeader = () => {
    return (
        <Header className="app-header">
            <div className="logo">T DoDream.</div>
            <Input
                className="search-input"
                placeholder="Search in Question"
                prefix={<SearchOutlined />}
            />
            <Menu className="header-menu" mode="horizontal" selectable={false}>
                <Menu.Item key="login" icon={<UserOutlined />}>
                    Log in
                </Menu.Item>
                <Menu.Item key="signup">
                    Sign Up
                </Menu.Item>
                <Menu.Item key="recent">
                    <Button type="primary">Retry</Button>
                </Menu.Item>
            </Menu>
        </Header>
    );
};

export default AppHeader;
