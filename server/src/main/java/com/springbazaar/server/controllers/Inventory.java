package com.springbazaar.server.controllers;

import com.springbazaar.server.entities.InventoryEntity;
import com.springbazaar.server.services.InventoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/inventory")
public class Inventory {
    private final InventoryService inventoryService;

    @Autowired
    public Inventory(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }
    @PostMapping("/product")
    @PreAuthorize("hasRole('ROLE_SELLER')")
    public InventoryEntity addProduct(@Valid @RequestBody InventoryEntity inventoryEntity, @CookieValue("Authorization") String jwtToken){
        return inventoryService.addProduct(inventoryEntity,jwtToken);
    }
    @PutMapping("/product")
    @PreAuthorize("hasRole('ROLE_SELLER')")
    public InventoryEntity updateProduct(@RequestBody InventoryEntity inventoryEntity){
        return inventoryService.updateProduct(inventoryEntity);
    }
    @DeleteMapping("/product/{id}")
    @PreAuthorize("hasRole('ROLE_SELLER')")
    public InventoryEntity removeProduct(@PathVariable Integer id){
        return inventoryService.removeProduct(id);
    }
}
