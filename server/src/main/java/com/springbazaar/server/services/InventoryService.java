package com.springbazaar.server.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.springbazaar.server.entities.InventoryEntity;
import com.springbazaar.server.exceptionHandlers.ApplicationException;
import com.springbazaar.server.repository.InventoryRepository;
import com.springbazaar.server.utils.JwtUtil;
import io.jsonwebtoken.JwtException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class InventoryService {
    private final InventoryRepository inventoryRepository;
    private final JwtUtil jwtUtil;
    @Value("${project.image}")
    private String path;
    @Value("${cloudinary.url}")
    private String CLOUDINARY_URL;

    @Autowired
    public InventoryService(InventoryRepository inventoryRepository, JwtUtil jwtUtil) {
        this.inventoryRepository = inventoryRepository;
        this.jwtUtil=jwtUtil;
    }
    public List<InventoryEntity> getAllSellerProducts(String jwtToken){
        try{
            String sellerId=jwtUtil.getSubjectFromToken(jwtToken);
            return inventoryRepository.findAllBySellerId(sellerId);
        }catch(DataAccessException e){
            throw new ApplicationException(HttpStatus.INTERNAL_SERVER_ERROR.value(),e.getMessage());
        }
    }
    public InventoryEntity addProduct(MultipartFile file, InventoryEntity inventoryEntity, String jwtToken){
        String sellerId=jwtUtil.getSubjectFromToken(jwtToken);
        String filePath = null;
        String fileName=sellerId+'-'+file.getOriginalFilename();
        try{
            filePath=uploadFile(sellerId,file);
        }
        catch(Exception e){
            System.out.println("error in catch: "+e.toString());
//            return new ResponseEntity<>(new CommonResponse("Error while saving the file: "+e.getMessage(),true,HttpStatus.INTERNAL_SERVER_ERROR.value()),HttpStatus.INTERNAL_SERVER_ERROR);
            throw new ApplicationException(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
        }
        inventoryEntity.setSellerId(sellerId);
        inventoryEntity.setItemPhoto(filePath);
        inventoryEntity.setFileName(fileName);
        System.out.println("made the entity: "+inventoryEntity.toString());
        return inventoryRepository.save(inventoryEntity);
    }
    public InventoryEntity updateProduct(InventoryEntity inventoryEntity){
        if (inventoryEntity.getId() == null){
            throw new ApplicationException(HttpStatus.BAD_REQUEST.value(), "Product Id can't be null");
        }
        Optional<InventoryEntity> item = inventoryRepository.findById(inventoryEntity.getId());
        if (item.isPresent()){
            String sellerId = item.get().getSellerId();
            inventoryEntity.setSellerId(sellerId);
            return inventoryRepository.save(inventoryEntity);
        }
        else{
            throw new ApplicationException(HttpStatus.NOT_FOUND.value(), "No such product found");
        }
    }
    public InventoryEntity removeProduct(Integer id){
        Optional<InventoryEntity> item = inventoryRepository.findById(id);
        if (item.isPresent()){
            inventoryRepository.deleteById(id);
            return item.get();
        }
        return null;
    }
    private String uploadFile(String userId, MultipartFile file) throws IOException {
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
