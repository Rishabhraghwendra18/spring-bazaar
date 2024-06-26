package com.springbazaar.server.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Date;

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
    @Column(name = "orderDate")
    private Date orderDate;
    @Column(name = "buyerId")
    private String buyerId;
    @Column(name = "deliveryAddress")
    private String deliveryAddress;
    @Column(name = "pinCode")
    private int pinCode;
    @Column(name = "itemId")
    private int itemId;
}
