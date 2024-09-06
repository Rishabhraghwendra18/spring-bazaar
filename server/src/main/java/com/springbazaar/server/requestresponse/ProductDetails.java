package com.springbazaar.server.requestresponse;

import com.springbazaar.server.utils.ProductSize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDetails{
    private Integer itemId;
    private ProductSize size;
}
