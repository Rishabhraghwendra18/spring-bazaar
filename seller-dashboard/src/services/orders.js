import apiClient from "./interceptor";

async function getSellerAllOrders() {
    return await apiClient.get("/order/getorders",{withCredentials:true});
}
async function getSellerDashboardAnalytics() {
    return apiClient.get("/order/sellerdashboard",{withCredentials:true});
}
async function getOrderById(id) {
    return apiClient.get(`/order/getOrders/${id}`,{withCredentials:true});
}
async function updateOrder(id,payload) {
    console.log("payment: ",payload)
    return apiClient.put(`/order/getOrders/${id}`,payload,{withCredentials:true});
}

export {getSellerAllOrders,getSellerDashboardAnalytics,getOrderById,updateOrder}