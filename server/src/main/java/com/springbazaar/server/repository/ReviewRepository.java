package com.springbazaar.server.repository;

import com.springbazaar.server.entities.InventoryEntity;
import com.springbazaar.server.entities.ReviewEntity;
import com.springbazaar.server.entities.UsersEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<ReviewEntity,Integer> {
    List<ReviewEntity> findByInventoryItemIdOrderByRatingDesc(InventoryEntity item, Pageable pageable);
    ReviewEntity findByUserId(UsersEntity user);
}
