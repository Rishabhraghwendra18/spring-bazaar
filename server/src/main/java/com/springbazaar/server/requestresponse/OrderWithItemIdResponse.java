package com.springbazaar.server.requestresponse;

import com.springbazaar.server.entities.InventoryEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class OrderWithItemIdResponse {
    private Integer orderId;
    private LocalDateTime orderDate;
    private String buyerId;
    private String deliveryAddress;
    private int pinCode;
    private boolean isCompleted;
    private ProductDetails productDetails;
    private InventoryEntity item;
}
