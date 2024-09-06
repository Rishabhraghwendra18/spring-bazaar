package com.springbazaar.server.requestresponse;

import com.springbazaar.server.utils.ProductSize;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderRequest {
    private String deliveryAddress;
    private int pinCode;
    private float orderValue;
    private List<ProductDetails> productDetails;
}