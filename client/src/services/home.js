import apiClient from "./interceptor";

async function getAllProducts() {
    return await apiClient.get(`/home/`);
}
async function getProductById(id) {
    return await apiClient.get(`/home/${id}`);
}

export {getAllProducts,getProductById}