package com.springbazaar.user_service.requestResponseModels;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import jakarta.validation.constraints.NotNull;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class LoginRequest {
    @NotNull(message = "email is required")
    private String email;
    @NotNull(message = "password is required")
    private String password;
}

