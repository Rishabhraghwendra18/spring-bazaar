package com.springbazaar.server.services;

import com.springbazaar.server.entities.InventoryEntity;
import com.springbazaar.server.entities.OrdersEntity;
import com.springbazaar.server.repository.InventoryRepository;
import com.springbazaar.server.repository.OrderRepository;
import com.springbazaar.server.requestresponse.*;
import com.springbazaar.server.utils.JwtUtil;
import com.springbazaar.server.utils.ProductSize;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {
    private OrderService orderServiceTest;
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private InventoryRepository inventoryRepository;
    @Mock
    private JwtUtil jwtUtil;
    @BeforeEach
    void setUp(){
        this.orderServiceTest = new OrderService(orderRepository,inventoryRepository,jwtUtil);
    }

    @Test
    public void testGetAllBuyerOrders_Success() {
        String jwtToken = "jwtToken";
        String userId = "userId";
        List<OrdersEntity> orders = new ArrayList<>();
        OrdersEntity order1 = new OrdersEntity();
        OrdersEntity order2 = new OrdersEntity();
        orders.add(order1);
        orders.add(order2);

        when(jwtUtil.getSubjectFromToken(jwtToken)).thenReturn(userId);
        when(orderRepository.findByBuyerId(userId)).thenReturn(orders);

        List<OrdersEntity> result = orderServiceTest.getAllBuyerOrders(jwtToken);

        assertEquals(2, result.size());
        verify(jwtUtil, times(1)).getSubjectFromToken(jwtToken);
        verify(orderRepository, times(1)).findByBuyerId(userId);
    }

    @Test
    public void testGetAllSellerOrders_Success() {
        String jwtToken = "jwtToken";
        String userId = "userId";
        List<OrderWithItemIdResponse> orders = new ArrayList<>();
        OrderWithItemIdResponse order1 = new OrderWithItemIdResponse(1, LocalDateTime.now(),"buyer@test.com","XYZ",111223,false,new ProductDetails(2, ProductSize.SMALL),new InventoryEntity());
        OrderWithItemIdResponse order2 = new OrderWithItemIdResponse(2, LocalDateTime.now(),"buyer2@test.com","ABC",1010101,true,new ProductDetails(6, ProductSize.LARGE),new InventoryEntity());
        orders.add(order1);
        orders.add(order2);

        when(jwtUtil.getSubjectFromToken(jwtToken)).thenReturn(userId);
        when(orderRepository.findOrdersBySellerId(userId)).thenReturn(orders);

        List<OrderWithItemIdResponse> result = orderServiceTest.getAllSellerOrders(jwtToken);

        assertEquals(2, result.size());
        verify(jwtUtil, times(1)).getSubjectFromToken(jwtToken);
        verify(orderRepository, times(1)).findOrdersBySellerId(userId);
    }

    @Test
    public void testGetSellerDashboardDetails_Success() {
        String jwtToken = "jwtToken";
        String userId = "userId";
        int totalOrders = 10;
        int completedOrders = 7;
        int newOrders = 3;

        when(jwtUtil.getSubjectFromToken(jwtToken)).thenReturn(userId);
        when(orderRepository.countOrdersBySellerId(userId)).thenReturn(totalOrders);
        when(orderRepository.countByOrderCompletedByStatusBySellerId(userId, true)).thenReturn(completedOrders);
        when(orderRepository.countByOrderCompletedByStatusBySellerId(userId, false)).thenReturn(newOrders);

        SellerDashboardResponse result = orderServiceTest.getSellerDashboardDetails(jwtToken);

        assertEquals(totalOrders, result.getTotalOrders());
        assertEquals(completedOrders, result.getCompletedOrders());
        assertEquals(newOrders, result.getNewOrders());
        verify(jwtUtil, times(1)).getSubjectFromToken(jwtToken);
        verify(orderRepository, times(1)).countOrdersBySellerId(userId);
        verify(orderRepository, times(1)).countByOrderCompletedByStatusBySellerId(userId, true);
        verify(orderRepository, times(1)).countByOrderCompletedByStatusBySellerId(userId, false);
    }

    @Test
    public void testGetOrderByOrderId_Success() {
        int orderId = 1;
        OrdersEntity order = new OrdersEntity();
        order.setOrderId(orderId);
        order.setItemId(new InventoryEntity());
        InventoryEntity inventoryEntity = new InventoryEntity();
        inventoryEntity.setItemTitle("title");
        inventoryEntity.setItemPhoto("photo");
        inventoryEntity.setFileName("file");

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        when(inventoryRepository.findById(order.getItemId().getId())).thenReturn(Optional.of(inventoryEntity));

        OrderDetailsResponse result = orderServiceTest.getOrderByOrderId(orderId);

        assertNotNull(result);
        assertEquals("title", result.getProductTitle());
        assertEquals("photo", result.getProductPhotoUrl());
        assertEquals("file", result.getFileName());
        verify(orderRepository, times(1)).findById(orderId);
        verify(inventoryRepository, times(1)).findById(order.getItemId().getId());
    }

    @Test
    public void testUpdateOrder_Success() {
        int orderId = 1;
        SellerOrderUpdateRequest updateRequest = new SellerOrderUpdateRequest();
        updateRequest.setCompleted(true);

        OrdersEntity order = new OrdersEntity();
        order.setOrderId(orderId);

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        when(orderRepository.save(any(OrdersEntity.class))).thenReturn(order);

        OrdersEntity result = orderServiceTest.updateOrder(orderId, updateRequest);

        assertNotNull(result);
        assertTrue(result.isCompleted());
        verify(orderRepository, times(1)).findById(orderId);
        verify(orderRepository, times(1)).save(order);
    }
}