package com.springbazaar.server.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    @Column(name = "itemId")
    private int itemId;
}
