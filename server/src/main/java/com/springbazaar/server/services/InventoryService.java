package com.springbazaar.server.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.springbazaar.server.entities.InventoryEntity;
import com.springbazaar.server.exceptionHandlers.ApplicationException;
import com.springbazaar.server.repository.InventoryRepository;
import com.springbazaar.server.utils.CloudinaryUploadFiles;
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
    private final CloudinaryUploadFiles cloudinaryUploadFiles;

    @Autowired
    public InventoryService(InventoryRepository inventoryRepository, JwtUtil jwtUtil, CloudinaryUploadFiles cloudinaryUploadFiles) {
        this.inventoryRepository = inventoryRepository;
        this.jwtUtil=jwtUtil;
        this.cloudinaryUploadFiles=cloudinaryUploadFiles;
    }
    public List<InventoryEntity> getAllSellerProducts(String jwtToken){
        try{
            String sellerId=jwtUtil.getSubjectFromToken(jwtToken);
            return inventoryRepository.findAllBySellerId(sellerId);
        }catch(DataAccessException e){
            throw new ApplicationException(HttpStatus.INTERNAL_SERVER_ERROR.value(),e.getMessage());
        }
    }
    public InventoryEntity getProductById(Integer id){
        return inventoryRepository.findById(id).orElseThrow(()-> new ApplicationException(HttpStatus.NOT_FOUND.value(), "Product with id: "+id+" not found"));
    }
    public InventoryEntity addProduct(MultipartFile file, InventoryEntity inventoryEntity, String jwtToken){
        String sellerId=jwtUtil.getSubjectFromToken(jwtToken);
        String filePath = null;
        String fileName=sellerId+'-'+file.getOriginalFilename();
        try{
            filePath=cloudinaryUploadFiles.uploadFile(sellerId,file);
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
    public InventoryEntity updateProduct(MultipartFile file,Integer id, Integer quantity,String description, float price,String title){
        InventoryEntity item = inventoryRepository.findById(id).orElseThrow(()-> new ApplicationException(HttpStatus.NOT_FOUND.value(), "No such product found"));
        String sellerId = item.getSellerId();
        item.setItemQuantity(quantity);
        item.setItemDescription(description);
        item.setItemPrice(price);
        item.setItemTitle(title);
        if (file !=null){
            try{
                String fileName=sellerId+'-'+file.getOriginalFilename();
                String filePath = cloudinaryUploadFiles.uploadFile(sellerId,file);
                item.setItemPhoto(filePath);
                item.setFileName(fileName);

            }catch(Exception e){
                System.out.println("Error while updating product photo: "+e);
                throw new ApplicationException(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error while updating product photo");
            }
        }
        return inventoryRepository.save(item);
    }
    public InventoryEntity removeProduct(Integer id){
        Optional<InventoryEntity> item = inventoryRepository.findById(id);
        if (item.isPresent()){
            inventoryRepository.deleteById(id);
            return item.get();
        }
        return null;
    }
}
