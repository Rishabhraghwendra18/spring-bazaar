package com.springbazaar.server.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.springbazaar.server.utils.PaymentState;
import com.springbazaar.server.utils.ProductSize;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter
@Setter
@ToString
@Table(name = "orders")
public class OrdersEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orderId")
    private Integer orderId;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Column(name = "orderDate")
    private LocalDateTime orderDate;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Column(name = "buyerId")
    private String buyerId;
    @Column(name = "deliveryAddress")
    private String deliveryAddress;
    @Column(name = "pinCode")
    private int pinCode;
    @Column(name="payment_state")
    @Enumerated(EnumType.STRING)
    private PaymentState paymentState;
    @Column(name = "razorpay_payment_id")
    private String razorPayPaymentId;
    @Column(name = "razorpay_order_id")
    private String razorPayOrderId;
    @Column(name = "razorpay_signature")
    private String razorPaySignature;
    @Column(name="productSize")
    @Enumerated(EnumType.STRING)
    private ProductSize productSize;
    @Column(name = "orderValue")
    private float orderValue;
    @Column(name = "isCompleted")
    private boolean isCompleted;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "itemId")
    private InventoryEntity itemId;
}
