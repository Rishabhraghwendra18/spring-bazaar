import apiClient from "./interceptor";

async function createUser(userData) {
    return await apiClient.post("/user/register",userData,{skipInterceptor:true});
}

export {createUser}