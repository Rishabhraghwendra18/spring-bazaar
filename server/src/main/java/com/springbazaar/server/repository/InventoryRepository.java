package com.springbazaar.server.repository;

import com.springbazaar.server.entities.InventoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InventoryRepository extends JpaRepository<InventoryEntity,Integer> {
    List<InventoryEntity> findAllBySellerId(String id);
    List<InventoryEntity> findByItemTitleContainingIgnoreCase(String title);
}
