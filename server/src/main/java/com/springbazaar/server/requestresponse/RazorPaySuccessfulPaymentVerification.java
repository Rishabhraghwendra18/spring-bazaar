package com.springbazaar.server.requestresponse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RazorPaySuccessfulPaymentVerification {
    private boolean isPaymentVerificationSuccessful;
}
