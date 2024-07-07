package com.springbazaar.server.controllers;

import com.springbazaar.server.entities.InventoryEntity;
import com.springbazaar.server.services.InventoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    public InventoryEntity addProduct(@RequestParam("file") MultipartFile file, @RequestParam("itemQuantity") int itemQuantity,@RequestParam("itemTitle") String itemTitle,@RequestParam("itemDescription") String itemDescription,@RequestParam("itemPrice") float itemPrice, @CookieValue("Authorization") String jwtToken){
        InventoryEntity inventoryEntity = new InventoryEntity();
        inventoryEntity.setItemQuantity(itemQuantity);
        inventoryEntity.setItemTitle(itemTitle);
        inventoryEntity.setItemDescription(itemDescription);
        inventoryEntity.setItemPrice(itemPrice);
        return inventoryService.addProduct(file,inventoryEntity,jwtToken);
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
