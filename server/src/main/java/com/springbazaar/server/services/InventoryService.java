package com.springbazaar.server.services;

import com.springbazaar.server.entities.InventoryEntity;
import com.springbazaar.server.repository.InventoryRepository;
import com.springbazaar.server.utils.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;

@Service
public class InventoryService {
    private final InventoryRepository inventoryRepository;
    private final JwtUtil jwtUtil;
    @Value("${project.image}")
    private String path;

    @Autowired
    public InventoryService(InventoryRepository inventoryRepository, JwtUtil jwtUtil) {
        this.inventoryRepository = inventoryRepository;
        this.jwtUtil=jwtUtil;
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
            return null;

        }
        inventoryEntity.setSellerId(sellerId);
        inventoryEntity.setItemPhoto(filePath);
        inventoryEntity.setFileName(fileName);
        System.out.println("made the entity: "+inventoryEntity.toString());
        return inventoryRepository.save(inventoryEntity);
    }
    public InventoryEntity updateProduct(InventoryEntity inventoryEntity){
        if (inventoryEntity.getId() == null){
            return null;
        }
        Optional<InventoryEntity> item = inventoryRepository.findById(inventoryEntity.getId());
        if (item.isPresent()){
            String sellerId = item.get().getSellerId();
            inventoryEntity.setSellerId(sellerId);
            return inventoryRepository.save(inventoryEntity);
        }
        return null;
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
        return f.getAbsoluteFile()+"/"+userId+'-'+originalFileName;
    }
}
