package com.springbazaar.server.requestresponse;

import com.springbazaar.server.utils.PaymentState;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RazorpayOrderUpdateRequest {
    private List<Integer> orderIds;
    private String razorpayPaymentId;
    private String razorpayOrderId;
    private String razorpaySignature;
}
