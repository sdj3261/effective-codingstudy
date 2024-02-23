import axios from "axios";

// 인증이 필요한 API에서 사용할 axios 객체
const authAxios = axios.create({
    baseUrl: `${process.env.REACT_APP_API}`,
    headers: {
        Authorization: `Bearer ${localStorage.getItem("accessToken")}`
    }
})
const refreshToken = async () => {
    try {
        const response = await axios.post(`${process.env.REACT_APP_API}/refresh`, {
            refreshToken: localStorage.getItem("refreshToken"),
        });
        const { accessToken } = response.data;
        localStorage.setItem("accessToken", accessToken);
        return accessToken;
    } catch (error) {
        console.error("Failed to refresh token", error);
        localStorage.clear();
        window.location.replace('/login');
        throw error;
    }
};

const handleErrorResponse = (error) => {
    if (error.response.data.errorCode === "A001") {
        alert(error.response.data.message);
        localStorage.clear();
        window.location.replace('/login');
    }

    return Promise.reject(error);
};



// interceptor를 사용해서 요청마다 header에 accessToken을 넣어줍니다.
authAxios.interceptors.request.use(
    (config) => {
        const accessToken = localStorage.getItem("accessToken");

        if(accessToken){
            config.headers.Authorization = `Bearer ${accessToken}`;
        }
        return config;
    },
    (error) => console.log("request interceptor error", error)
)

authAxios.interceptors.response.use(
    (response) => response,
    async (error) => {
        const originalRequest = error.config;
        if (error.response.data.message === "ACCESS_TOKEN_EXPIRED"  && !originalRequest._retry) {
            originalRequest._retry = true; // 재시도 플래그 설정
            try {
                const accessToken = await refreshToken(); // 새 액세스 토큰 요청
                authAxios.defaults.headers.common['Authorization'] = `Bearer ${accessToken}`;
                originalRequest.headers['Authorization'] = `Bearer ${accessToken}`;
                return authAxios(originalRequest);
            } catch (refreshError) {
                return Promise.reject(refreshError);
            }
        }

        return handleErrorResponse(error); // 에러 처리
    }
);

export default authAxios;