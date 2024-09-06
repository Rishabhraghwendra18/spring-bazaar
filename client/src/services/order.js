import apiClient from "./interceptor";

async function createOrder(payload) {
    return await apiClient.post("/order/purchase",payload);
}
async function verifyAndUpdateOrder(payload) {
    return await apiClient.put("/order/purchase",payload);
}
export {createOrder,verifyAndUpdateOrder}