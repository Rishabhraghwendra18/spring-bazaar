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
async function getProductById(id) {
    return await apiClient.get(`/inventory/product/${id}`);
}
async function updateInventoryProduct(payload) {
    return await apiClient.put("/inventory/product",payload,{
        headers: {
            'Content-Type': 'multipart/form-data'
        }
    },{withCredentials:true});
}

export {getSellerAllProducts,addProductInInventory,updateInventoryProduct,getProductById}