/** @type {import('next').NextConfig} */
const nextConfig = {
    images:{
        remotePatterns:[
            {
                protocol:"https",
                hostname:"res.cloudinary.com",
                port:'',
                pathname:'/dmil68pkd/**'
            }
        ]
    }
};

export default nextConfig;
