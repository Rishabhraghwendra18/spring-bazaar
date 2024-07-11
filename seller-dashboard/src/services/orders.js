import apiClient from "./interceptor";

async function getSellerAllOrders() {
    return await apiClient.get("/order/getorders",{withCredentials:true});
}

export {getSellerAllOrders}