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
public class RazorpayCreateOrderResponse {
    private String id;
    private String entity;
    private int amount;
    private int amountPaid;
    private int amountDue;
    private String currency;
    private String receipt;
    private String offerId;
    private String status;
    private int attempts;
    private List<String> notes;
    private long createdAt;
}
