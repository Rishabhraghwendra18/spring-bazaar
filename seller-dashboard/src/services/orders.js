import apiClient from "./interceptor";

async function getSellerAllOrders() {
    return await apiClient.get("/order/getorders",{withCredentials:true});
}
async function getSellerDashboardAnalytics() {
    return apiClient.get("/order/sellerdashboard",{withCredentials:true});
}

export {getSellerAllOrders,getSellerDashboardAnalytics}