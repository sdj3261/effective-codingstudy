import axios from 'axios';
import { message } from 'antd';
import { useNavigate } from 'react-router-dom';

export const useAxios = () => {
    const navigate = useNavigate();

    const sendRequest = async ({ endpoint, method, data, redirect = { path: '', shouldRedirect: false } }) => {
        const url = `${process.env.REACT_APP_API}/${endpoint}`;

        try {
            const res = await axios({ url, method, data });
            message.success(res.data.message);
            if (redirect.shouldRedirect) {
                navigate(redirect.path, { replace: true });
            }
        } catch (e) {
            message.error(e.response?.data.msg || "요청 처리 중 오류가 발생했습니다.");
        }
    };

    return sendRequest;
};
