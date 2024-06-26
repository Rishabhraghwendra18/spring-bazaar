package com.springbazaar.server.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@Table(name = "inventory")
public class InventoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "sellerId")
    private String sellerId;
    @Column(name = "itemQuantity")
    private int itemQuantity;
    @Column(name = "itemTitle")
    private String itemTitle;
    @Column(name = "itemDescription")
    private String itemDescription;
    @Column(name = "itemPrice")
    private float itemPrice;
    @Column(name = "itemPhoto")
    private String itemPhoto;
}
