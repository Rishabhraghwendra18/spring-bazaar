package com.springbazaar.server.services;

import com.springbazaar.server.controllers.Order;
import com.springbazaar.server.entities.InventoryEntity;
import com.springbazaar.server.entities.OrdersEntity;
import com.springbazaar.server.repository.InventoryRepository;
import com.springbazaar.server.repository.OrderRepository;
import com.springbazaar.server.requestresponse.OrderRequest;
import com.springbazaar.server.requestresponse.OrderWithItemIdResponse;
import com.springbazaar.server.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final InventoryRepository inventoryRepository;
    private final JwtUtil jwtUtil;

    @Autowired
    public OrderService(OrderRepository orderRepository, InventoryRepository inventoryRepository,JwtUtil jwtUtil) {

        this.orderRepository = orderRepository;
        this.inventoryRepository=inventoryRepository;
        this.jwtUtil=jwtUtil;
    }
    public List<OrdersEntity> getAllBuyerOrders (String jwtToken){
        var claims = jwtUtil.getAllClaimsFromToken(jwtToken);
        String userId = claims.getSubject();
        return orderRepository.findByBuyerId(userId);
    }
    public OrdersEntity createOrder(OrderRequest orderRequest, String jwtToken){
        //        Updating Item Quantity
        Optional<InventoryEntity> itemQuantityUpdate = inventoryRepository.findById(orderRequest.getItemId());
        if(itemQuantityUpdate.isPresent() && itemQuantityUpdate.get().getItemQuantity() == 0) return null;
        if (itemQuantityUpdate.isPresent() && itemQuantityUpdate.get().getItemQuantity() - 1 >= 0){
            itemQuantityUpdate.get().setItemQuantity(itemQuantityUpdate.get().getItemQuantity()-1);
            inventoryRepository.save(itemQuantityUpdate.get());
        }

        LocalDateTime orderDate = LocalDateTime.now();
        var claims = jwtUtil.getAllClaimsFromToken(jwtToken);
        String userId = claims.getSubject();
        OrdersEntity order = new OrdersEntity();
        order.setBuyerId(userId);
        order.setOrderDate(orderDate);
        order.setDeliveryAddress(orderRequest.getDeliveryAddress());
        order.setPinCode(orderRequest.getPinCode());
        InventoryEntity item = new InventoryEntity();
        item.setId(orderRequest.getItemId());
        order.setItemId(item);

        return orderRepository.save(order);
    }
    public List<OrderWithItemIdResponse> getAllSellerOrders(String token){
        var claims = jwtUtil.getAllClaimsFromToken(token);
        String userId = claims.getSubject();
        return orderRepository.findOrdersBySellerId(userId);
    }
}
