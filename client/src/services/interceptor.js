import axios from "axios";
import { getCookie } from "cookies-next";

const apiClient = axios.create({
  baseURL: process.env.NEXT_PUBLIC_SERVER_PORT,
  headers: {
    "Content-Type": "application/json",
  },
});

apiClient.interceptors.request.use(
  (config) => {

    if(config.skipInterceptor){
        return config;
    }

    const token = getCookie("Authorization");
    console.log("token is: ",token);
    if (token) {
      config.headers.Authorization = token;
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

export default apiClient;
