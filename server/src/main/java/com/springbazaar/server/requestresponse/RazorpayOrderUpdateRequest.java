package com.springbazaar.server.requestresponse;

import com.springbazaar.server.utils.PaymentState;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RazorpayOrderUpdateRequest {
    private int orderId;
    private PaymentState paymentStatus;
    private String razorpayPaymentId;
    private String razorpayOrderId;
    private String razorpaySignature;
}
