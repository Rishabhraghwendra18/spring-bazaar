package com.springbazaar.server.requestresponse;

import com.springbazaar.server.utils.ProductSize;
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
    private float orderValue;
    private List<ProductDetails> productDetails;
}