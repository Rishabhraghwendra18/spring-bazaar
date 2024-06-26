package com.springbazaar.server.repository;

import com.springbazaar.server.entities.OrdersEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrdersEntity,Integer> {
}
