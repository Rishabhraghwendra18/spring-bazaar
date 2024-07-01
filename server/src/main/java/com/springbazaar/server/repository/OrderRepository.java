package com.springbazaar.server.repository;

import com.springbazaar.server.entities.OrdersEntity;
import com.springbazaar.server.requestresponse.OrderWithItemIdResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<OrdersEntity,Integer> {
    List<OrdersEntity> findByBuyerId(String buyerId);
    @Query("SELECT new com.springbazaar.server.requestresponse.OrderWithItemIdResponse(o.orderId, o.orderDate, o.buyerId, o.deliveryAddress, o.pinCode, i) " +
            "FROM OrdersEntity o JOIN o.itemId i WHERE i.sellerId = ?1")
    List<OrderWithItemIdResponse> findOrdersBySellerId(String sellerId);
}
