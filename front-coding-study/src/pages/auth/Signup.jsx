import React, { useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import {Form, Input, Button, message, Modal, Space, Row, Col} from 'antd';
import {useAxios} from "../../hook/CustomAxios";
import DaumPostcode from 'react-daum-postcode';

const Signup = () => {
    const [user, setUser] = useState({
        username: "",
        email: "",
        password: "",
        confirmPassword: "",
        address: "" // 주소 상태 추가
    });

    const [isModalVisible, setIsModalVisible] = useState(false); // 모달 상태

    const handleAddress = (data) => {
        let fullAddress = data.address;
        let extraAddress = '';

        if (data.addressType === 'R') {
            if (data.bname !== '') {
                extraAddress += data.bname;
            }
            if (data.buildingName !== '') {
                extraAddress += (extraAddress !== '' ? `, ${data.buildingName}` : data.buildingName);
            }
            fullAddress += (extraAddress !== '' ? ` (${extraAddress})` : '');
        }

        setUser({ ...user, address: fullAddress });
        setIsModalVisible(false); // 우편번호 검색 후 모달 닫기
    };

    // 모달을 열고 닫는 함수
    const showModal = () => {
        setIsModalVisible(true);
    };

    const handleCancel = () => {
        setIsModalVisible(false);
    };
    const { sendRequest } = useAxios();

    const onChange = (e) => {
        const { name, value } = e.target;
        setUser({ ...user, [name]: value });
    };

    const submit = async () => {
        if(user.password !== user.confirmPassword) {
            message.error("비밀번호가 일치하지 않습니다.");
            return;
        }
        sendRequest({
            endpoint: 'signup',
            method: 'post',
            data: user,
            redirect: {
                path: '/login', // 요청 성공 후 이동할 경로
                shouldRedirect: true // 성공시 리다이렉트 여부
            }
        });
    };

    return (
        <Form onFinish={submit}>
            <Form.Item
                name="username"
                rules={[{ required: true, message: 'Please input your username!' }]}
            >
                <Input placeholder="Username" onChange={onChange} />
            </Form.Item>
            <Form.Item
                name="email"
                rules={[{ required: true, message: 'Please input your Email!' }]}
            >
                <Input placeholder="Email" onChange={onChange} />
            </Form.Item>
            <Form.Item
                name="password"
                rules={[{ required: true, message: 'Please input your Password!' }]}
            >
                <Input.Password placeholder="Password" onChange={onChange} />
            </Form.Item>
            <Form.Item
                name="confirmPassword"
                rules={[{ required: true, message: 'Please confirm your Password!' }]}
            >
                <Input.Password placeholder="Confirm Password" onChange={onChange} />
            </Form.Item>
            <Form.Item>
                <Row gutter={8}>
                    <Col span={18}>
                        <Form.Item
                            name="address"
                            noStyle
                            rules={[{ required: true, message: 'Please confirm your Address!' }]}
                        >
                            <Input readOnly placeholder="Address" />
                        </Form.Item>
                    </Col>
                    <Col span={6}>
                        <Button type="primary" onClick={showModal}>
                            주소 찾기
                        </Button>
                    </Col>
                </Row>
                <Row gutter={8} style={{ marginTop: '8px' }}>
                    <Col span={24}>
                        <Form.Item
                            name="detailAddress"
                            rules={[{ required: true, message: 'Please input your detail address!' }]}
                        >
                            <Input placeholder="Detail Address" />
                        </Form.Item>
                    </Col>
                </Row>
                <Modal title="주소 검색" visible={isModalVisible} onCancel={handleCancel} footer={null}>
                    <DaumPostcode onComplete={handleAddress} />
                </Modal>
            </Form.Item>
            <Form.Item>
                <Button type="primary" htmlType="submit">
                    회원가입
                </Button>
            </Form.Item>
        </Form>
    );
};

export default Signup;
