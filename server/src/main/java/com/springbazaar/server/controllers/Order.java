package com.springbazaar.server.controllers;

import com.springbazaar.server.entities.OrdersEntity;
import com.springbazaar.server.entities.UsersEntity;
import com.springbazaar.server.repository.OrderRepository;
import com.springbazaar.server.requestresponse.OrderRequest;
import com.springbazaar.server.requestresponse.OrderWithItemIdResponse;
import com.springbazaar.server.services.OrderService;
import com.springbazaar.server.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/order")
public class Order {
//    1. /purchase
//    2. /getAllOrder - only buyers
//    3. /getAllOrders - only sellers
    private final OrderService orderService;

    @Autowired
    public Order(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/buyerOrders")
    @PreAuthorize("hasRole('ROLE_BUYER')")
    public List<OrdersEntity> getAllBuyerOrders(@CookieValue("Authorization") String jwtToken){
        return orderService.getAllBuyerOrders(jwtToken);
    }
    @PostMapping("/purchase")
    public OrdersEntity createOrder(@RequestBody OrderRequest orderRequest, @CookieValue("Authorization") String jwtToken){
        return orderService.createOrder(orderRequest,jwtToken);
    }
    @GetMapping("/getorders")
    @PreAuthorize("hasRole('ROLE_SELLER')")
    public List<OrderWithItemIdResponse> getAllSellerOrders(@CookieValue("Authorization") String jwtToken){
        return orderService.getAllSellerOrders(jwtToken);
    }
}
