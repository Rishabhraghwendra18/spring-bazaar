package com.springbazaar.server.requestresponse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {
    private String deliveryAddress;
    private int pinCode;
    private List<Integer> itemIds;
    private float orderValue;
}
