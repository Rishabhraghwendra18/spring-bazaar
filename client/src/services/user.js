import apiClient from "./interceptor";

async function createUser(userData) {
    return await apiClient.post("/user/register",userData,{skipInterceptor:true});
}
async function loginUser(userData) {
    return await apiClient.post("/user/login",userData,{skipInterceptor:true});
}

export {createUser,loginUser}