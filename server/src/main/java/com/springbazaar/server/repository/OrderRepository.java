package com.springbazaar.server.repository;

import com.springbazaar.server.entities.OrdersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<OrdersEntity,Integer> {
    List<OrdersEntity> findByBuyerId(String buyerId);
//    @Query("SELECT o FROM orders o, inventory i WHERE o.itemId = i.id AND i.sellerId = ?1")
//    List<OrdersEntity> findOrdersBySellerId(String sellerId);
}
