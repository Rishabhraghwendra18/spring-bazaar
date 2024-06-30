package com.springbazaar.server.services;

import com.springbazaar.server.entities.OrdersEntity;
import com.springbazaar.server.repository.OrderRepository;
import com.springbazaar.server.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final JwtUtil jwtUtil;

    @Autowired
    public OrderService(OrderRepository orderRepository, JwtUtil jwtUtil) {

        this.orderRepository = orderRepository;
        this.jwtUtil=jwtUtil;
    }
    public List<OrdersEntity> getAllBuyerOrders (String jwtToken){
        var claims = jwtUtil.getAllClaimsFromToken(jwtToken);
        String userId = claims.getSubject();
        return orderRepository.findByBuyerId(userId);
    }
    public OrdersEntity createOrder(OrdersEntity ordersEntity,String jwtToken){
        LocalDateTime orderDate = LocalDateTime.now();
        var claims = jwtUtil.getAllClaimsFromToken(jwtToken);
        String userId = claims.getSubject();
        ordersEntity.setBuyerId(userId);
        ordersEntity.setOrderDate(orderDate);
        return orderRepository.save(ordersEntity);
    }
}
