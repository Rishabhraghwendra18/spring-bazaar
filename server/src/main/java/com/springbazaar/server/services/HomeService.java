package com.springbazaar.server.services;

import com.springbazaar.server.entities.InventoryEntity;
import com.springbazaar.server.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HomeService {
    private final InventoryRepository inventoryRepository;

    @Autowired
    public HomeService(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }
    public List<InventoryEntity> getAllInventoryItems(){
        return inventoryRepository.findAll();
    }
    public List<InventoryEntity> getTopInventoryItems(int limit) {
        Pageable pageable = PageRequest.of(0, limit);
        return inventoryRepository.findAll(pageable).getContent();
    }
    public InventoryEntity getItemById(Integer id){
        Optional<InventoryEntity> item = inventoryRepository.findById(id);
        return item.orElse(null);
    }
    public List<InventoryEntity> searchProductsByTitle(String title) {
        return inventoryRepository.findByItemTitleContainingIgnoreCase(title);
    }
}
