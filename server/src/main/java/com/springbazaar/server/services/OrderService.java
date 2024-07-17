package com.springbazaar.server.services;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.razorpay.Utils;
import com.springbazaar.server.entities.InventoryEntity;
import com.springbazaar.server.entities.OrdersEntity;
import com.springbazaar.server.exceptionHandlers.ApplicationException;
import com.springbazaar.server.repository.InventoryRepository;
import com.springbazaar.server.repository.OrderRepository;
import com.springbazaar.server.requestresponse.*;
import com.springbazaar.server.utils.JwtUtil;
import com.springbazaar.server.utils.PaymentState;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final InventoryRepository inventoryRepository;
    private final JwtUtil jwtUtil;
    @Value("${razorpay.key.id}")
    private String razorpayKeyId;
    @Value("${razorpay.key.secret}")
    private String razorPayKeySecret;

    @Autowired
    public OrderService(OrderRepository orderRepository, InventoryRepository inventoryRepository,JwtUtil jwtUtil) {

        this.orderRepository = orderRepository;
        this.inventoryRepository=inventoryRepository;
        this.jwtUtil=jwtUtil;
    }
    public List<OrdersEntity> getAllBuyerOrders (String jwtToken){
        String userId = jwtUtil.getSubjectFromToken(jwtToken);
        return orderRepository.findByBuyerId(userId);
    }
    @Transactional
    public RazorpayCreateOrderResponse createOrder(OrderRequest orderRequest, String jwtToken){
        LocalDateTime orderDate = LocalDateTime.now();
        String userId = jwtUtil.getSubjectFromToken(jwtToken);

        Integer totalOrdersId=0;
        List<Integer> orderIdsList = new ArrayList<>();
        for(Integer itemId: orderRequest.getItemIds()){
            //        Updating Item Quantity
            Optional<InventoryEntity> itemQuantityUpdate = inventoryRepository.findById(itemId);
            if(itemQuantityUpdate.isPresent() && itemQuantityUpdate.get().getItemQuantity() == 0) return null;
            if (itemQuantityUpdate.isPresent() && itemQuantityUpdate.get().getItemQuantity() - 1 >= 0){
                itemQuantityUpdate.get().setItemQuantity(itemQuantityUpdate.get().getItemQuantity()-1);
                inventoryRepository.save(itemQuantityUpdate.get());
            }


            OrdersEntity order = new OrdersEntity();
            order.setBuyerId(userId);
            order.setOrderDate(orderDate);
            order.setDeliveryAddress(orderRequest.getDeliveryAddress());
            order.setPinCode(orderRequest.getPinCode());
            order.setPaymentState(PaymentState.CREATED);
            order.setProductSize(orderRequest.getSize());
            order.setOrderValue(orderRequest.getOrderValue());

            InventoryEntity item = new InventoryEntity();
            item.setId(itemId);
            order.setItemId(item);
            totalOrdersId+=itemId;

//        Saving the order
            System.out.println("orders object: "+order.toString());
            OrdersEntity savedCreatedOrder = orderRepository.save(order);
            orderIdsList.add(savedCreatedOrder.getOrderId());

        }
//        Creating Razorpay Order
        try{
            RazorpayClient razorpayClient = new RazorpayClient(this.razorpayKeyId,this.razorPayKeySecret);
            JSONObject razorPayOrderRequest = new JSONObject();
            razorPayOrderRequest.put("amount",orderRequest.getOrderValue()*100);
            razorPayOrderRequest.put("currency", "INR");
            razorPayOrderRequest.put("receipt", totalOrdersId.toString());
            System.out.println("order request: "+razorPayOrderRequest.toString());
            // Setting content type and accept headers
//            Map<String, String> headers = new HashMap<>();
//            headers.put("Content-Type", "application/json");
//            headers.put("Accept", "application/json");
//            razorpayClient.addHeaders(headers);
            Order createdOrder = razorpayClient.orders.create(razorPayOrderRequest);
            RazorpayCreateOrderResponse response = new RazorpayCreateOrderResponse();
            response.setId(createdOrder.get("id") != null ? createdOrder.get("id").toString() : null);
            response.setEntity(createdOrder.get("entity") != null ? createdOrder.get("entity").toString() : null);
            response.setAmount(createdOrder.get("amount") != null ? createdOrder.get("amount") : 0);
            response.setAmountPaid(createdOrder.get("amount_paid") != null ? createdOrder.get("amount_paid") : 0);
            response.setAmountDue(createdOrder.get("amount_due") != null ? createdOrder.get("amount_due") : 0);
            response.setCurrency(createdOrder.get("currency") != null ? createdOrder.get("currency").toString() : null);
            response.setReceipt(createdOrder.get("receipt") != null ? createdOrder.get("receipt").toString() : null);
            response.setOfferId(createdOrder.get("offer_id") != null ? createdOrder.get("offer_id").toString() : null);
            response.setStatus(createdOrder.get("status") != null ? createdOrder.get("status").toString() : null);
            response.setAttempts(createdOrder.get("attempts") != null ? Integer.parseInt(createdOrder.get("attempts").toString()) : 0);
            response.setOrderIdsList(orderIdsList);
            return response;
//            return null;
        }catch(RazorpayException e){
            System.out.println("Exception while creating razorpay order: "+e.getMessage());
            throw new ApplicationException(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
        }

    }
    public RazorPaySuccessfulPaymentVerification verifyAndUpdateOrderStatus(RazorpayOrderUpdateRequest razorpayOrderUpdateRequest){

        try{
            RazorpayClient razorpayClient = new RazorpayClient(this.razorpayKeyId,this.razorPayKeySecret);
            JSONObject options = new JSONObject();
            options.put("razorpay_order_id", razorpayOrderUpdateRequest.getRazorpayOrderId());
            options.put("razorpay_payment_id", razorpayOrderUpdateRequest.getRazorpayPaymentId());
            options.put("razorpay_signature", razorpayOrderUpdateRequest.getRazorpaySignature());
            boolean status =  Utils.verifyPaymentSignature(options, this.razorPayKeySecret);
            if (!status){
                throw new ApplicationException(HttpStatus.BAD_REQUEST.value(), "Payment Verification Failed");
            }
            for(Integer orders:razorpayOrderUpdateRequest.getOrderIds()){
                Optional<OrdersEntity> order = orderRepository.findById(orders);
                OrdersEntity orderEntity = getOrdersEntity(razorpayOrderUpdateRequest, order,PaymentState.CAPTURED);
                orderRepository.save(orderEntity);
            }
            return new RazorPaySuccessfulPaymentVerification(true);
        }
        catch(RazorpayException e){
            System.out.println("Exception while verifying order: "+e.getMessage());
            for(Integer orders:razorpayOrderUpdateRequest.getOrderIds()){
                Optional<OrdersEntity> order = orderRepository.findById(orders);
                OrdersEntity orderEntity = getOrdersEntity(razorpayOrderUpdateRequest, order,PaymentState.FAILED);
                orderRepository.save(orderEntity);
            }
            throw new ApplicationException(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
        }
        catch (ApplicationException e){
            for(Integer orders:razorpayOrderUpdateRequest.getOrderIds()){
                Optional<OrdersEntity> order = orderRepository.findById(orders);
                OrdersEntity orderEntity = getOrdersEntity(razorpayOrderUpdateRequest, order,PaymentState.FAILED);
                orderRepository.save(orderEntity);
            }
            throw new ApplicationException(e.getErrorCode(),e.getMessage());
        }
    }

    @NotNull
    private static OrdersEntity getOrdersEntity(RazorpayOrderUpdateRequest razorpayOrderUpdateRequest, Optional<OrdersEntity> order,PaymentState paymentState) {
        OrdersEntity orderEntity = order.orElseThrow(() -> new ApplicationException(HttpStatus.NOT_FOUND.value(), "Order with order id: "+ razorpayOrderUpdateRequest.getOrderIds()+" not found"));
        orderEntity.setPaymentState(paymentState);
        orderEntity.setRazorPaySignature(razorpayOrderUpdateRequest.getRazorpaySignature());
        orderEntity.setRazorPayOrderId(razorpayOrderUpdateRequest.getRazorpayOrderId());
        orderEntity.setRazorPayPaymentId(razorpayOrderUpdateRequest.getRazorpayPaymentId());
        return orderEntity;
    }

    public List<OrderWithItemIdResponse> getAllSellerOrders(String token){
        String userId = jwtUtil.getSubjectFromToken(token);
        return orderRepository.findOrdersBySellerId(userId);
    }
}
