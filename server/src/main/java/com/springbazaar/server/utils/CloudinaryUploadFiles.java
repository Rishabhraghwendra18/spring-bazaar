package com.springbazaar.server.utils;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.springbazaar.server.exceptionHandlers.ApplicationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

@Component
public class CloudinaryUploadFiles {
    @Value("${project.image}")
    private String path;
    @Value("${cloudinary.url}")
    private String CLOUDINARY_URL;

    public String uploadFile(String userId, MultipartFile file) throws IOException {
        String originalFileName=file.getOriginalFilename(); // original file name
        String fullFilePath = path + File.separator+ userId +'-'+originalFileName; // file name with user id to fetch easily
        File f = new File(path);
        if (!f.exists()){
            f.mkdir(); // create folder if doesn't exist
        }
        Files.copy(file.getInputStream(), Paths.get(fullFilePath)); // save file at the path

        Cloudinary cloudinary = new Cloudinary(CLOUDINARY_URL);
        cloudinary.config.secure = true;
        Map params = ObjectUtils.asMap(
                "use_filename", true,
                "unique_filename", false,
                "overwrite", true
        );
        String uploadedPhotoUrl;
        String localFilePath=f.getAbsoluteFile()+"/"+userId+'-'+originalFileName;
        try{
            uploadedPhotoUrl = (String) cloudinary.uploader().upload(localFilePath,params).get("secure_url");
            deleteFileFromLocal(localFilePath);
            return uploadedPhotoUrl;
        }catch(Exception e){
            System.out.println("Exception while uploading image to cloudinary: "+e.toString());
            throw new ApplicationException(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Not able to upload Product Photo");
        }
    }
    private void deleteFileFromLocal(String filePath){
        File file = new File(filePath);
        if (file.exists()){
            boolean isDeleted = file.delete();
            if (!isDeleted){
                System.out.println("Not able to delete file");
            }
        }
        else{
            System.out.println("File don't exist at path");
        }
    }
}
