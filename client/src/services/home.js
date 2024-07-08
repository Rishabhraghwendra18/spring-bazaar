import axios from "axios";
import apiClient from "./interceptor";

const baseURL=process.env.NEXT_PUBLIC_SERVER_PORT;

// async function getAllProducts() {
//     return await axios.get(`${baseURL}/home`,{
//         withCredentials:true
//     });
// }
async function getAllProducts() {
    return await apiClient.get(`/home/`);
}

export {getAllProducts}