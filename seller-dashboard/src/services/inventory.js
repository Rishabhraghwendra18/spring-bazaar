import apiClient from "./interceptor";

async function addProductInInventory(payload) {
    return await apiClient.post("/inventory/product",payload,{
        headers: {
            'Content-Type': 'multipart/form-data'
        }
    },{withCredentials:true});
}
async function getSellerAllProducts() {
    return await apiClient.get("/inventory/product");
}

export {getSellerAllProducts,addProductInInventory}