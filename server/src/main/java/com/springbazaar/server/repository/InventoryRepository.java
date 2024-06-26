package com.springbazaar.server.repository;

import com.springbazaar.server.entities.InventoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepository extends JpaRepository<InventoryEntity,Integer> {
}
