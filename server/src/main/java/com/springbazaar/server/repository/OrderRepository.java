package com.springbazaar.server.repository;

import com.springbazaar.server.entities.OrdersEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<OrdersEntity,Integer> {
    List<OrdersEntity> findByBuyerId(String buyerId);
}
