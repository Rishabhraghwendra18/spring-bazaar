package com.springbazaar.server.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class users {
    @PostMapping("/login")
    public String login(){
        return "Logged In";
    }
    @PostMapping("/register")
    public String register(){
        return "User register";
    }
}
