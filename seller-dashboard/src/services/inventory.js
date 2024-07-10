import apiClient from "./interceptor";

async function addProductInInventory(payload) {
    return await apiClient.post("/inventory/product",payload,{
        headers: {
            'Content-Type': 'multipart/form-data'
        }
    },{withCredentials:true});
}

export {addProductInInventory}