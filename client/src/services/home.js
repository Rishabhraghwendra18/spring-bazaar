import apiClient from "./interceptor";

async function getAllProducts() {
    return await apiClient.get(`/home/`);
}
async function getProductById(id) {
    return await apiClient.get(`/home/${id}`);
}
async function searchProductByTitle(query) {
    return await apiClient.get(`/home/search?query=${query}`);
}

export {getAllProducts,getProductById,searchProductByTitle}