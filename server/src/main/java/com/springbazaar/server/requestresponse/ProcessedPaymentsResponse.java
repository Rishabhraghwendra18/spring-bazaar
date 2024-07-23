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
public class ProcessedPaymentsResponse {
    private Integer orderId;
    private float orderValue;
    private PaymentState paymentState;
}
