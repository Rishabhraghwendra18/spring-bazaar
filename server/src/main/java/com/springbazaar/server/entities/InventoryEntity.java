package com.springbazaar.server.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

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
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Column(name = "sellerId")
    private String sellerId;
    @NotNull(message = "item quantity is required")
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
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Column(name = "file_name")
    private String fileName;

    @JsonIgnore
    @OneToMany(mappedBy = "itemId",cascade = {CascadeType.REMOVE})
    private List<OrdersEntity> orders;
}
