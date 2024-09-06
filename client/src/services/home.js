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
async function getProductsForHomePage(limit) {
    return await apiClient.get(`/home/?limit=${limit}`);
}

export {getAllProducts,getProductById,searchProductByTitle,getProductsForHomePage}