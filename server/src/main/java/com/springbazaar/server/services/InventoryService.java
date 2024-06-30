package com.springbazaar.server.services;

import com.springbazaar.server.entities.InventoryEntity;
import com.springbazaar.server.repository.InventoryRepository;
import com.springbazaar.server.utils.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InventoryService {
    private final InventoryRepository inventoryRepository;
    private final JwtUtil jwtUtil;

    @Autowired
    public InventoryService(InventoryRepository inventoryRepository, JwtUtil jwtUtil) {
        this.inventoryRepository = inventoryRepository;
        this.jwtUtil=jwtUtil;
    }
    public InventoryEntity addProduct(InventoryEntity inventoryEntity, String jwtToken){
        String sellerId=jwtUtil.getSubjectFromToken(jwtToken);
        inventoryEntity.setSellerId(sellerId);
        return inventoryRepository.save(inventoryEntity);
    }
}
