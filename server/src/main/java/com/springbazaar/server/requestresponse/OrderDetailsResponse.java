package com.springbazaar.server.requestresponse;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.springbazaar.server.utils.PaymentState;
import com.springbazaar.server.utils.ProductSize;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderDetailsResponse {
    private Integer orderId;
    private LocalDateTime orderDate;
    private String buyerId;
    private String deliveryAddress;
    private int pinCode;
    private PaymentState paymentState;
    private String razorPayPaymentId;
    private String razorPayOrderId;
    private String razorPaySignature;
    private ProductSize productSize;
    private float orderValue;
    private boolean isCompleted;
    private String productTitle;
    private String productPhotoUrl;
    private String fileName;
}
