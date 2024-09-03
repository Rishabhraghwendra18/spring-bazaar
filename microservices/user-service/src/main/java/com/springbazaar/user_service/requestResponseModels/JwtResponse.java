package com.springbazaar.user_service.requestResponseModels;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class JwtResponse {
    private String token;
    private String email;
    private String message;
    private boolean error;
    private Date expirationDate;
}

