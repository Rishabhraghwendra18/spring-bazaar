package com.springbazaar.user_service.controllers;

import com.springbazaar.user_service.entities.UsersEntity;
import com.springbazaar.user_service.requestResponseModels.JwtResponse;
import com.springbazaar.user_service.requestResponseModels.LoginRequest;
import com.springbazaar.user_service.services.UserService;
import com.springbazaar.user_service.utils.JwtUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class Users {
    private final UserService userService;
    private final JwtUtil jwtUtil;
    @Autowired
    public Users(UserService userService, JwtUtil jwtUtil){
        this.userService=userService;
        this.jwtUtil=jwtUtil;
    }
    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@Valid @RequestBody LoginRequest loginRequest, HttpServletResponse clientResponse){
        var jwtToken = userService.login(loginRequest);
        Cookie cookie = new Cookie("Authorization",jwtToken);
        cookie.setPath("/");
        cookie.setDomain("localhost");
        cookie.setSecure(false);
        clientResponse.addCookie(cookie);
        JwtResponse jwtResponse = new JwtResponse(jwtToken,loginRequest.getEmail(),"Login successfull",false,jwtUtil.getExpirationDateFromToken(jwtToken));
        return new ResponseEntity<>(jwtResponse, HttpStatus.OK);
    }
    @PostMapping("/register")
    public ResponseEntity<Object> register(@Valid @RequestBody UsersEntity user){
        var res = userService.createUser(user);
        return ResponseEntity.status(HttpStatus.OK).body(new Object(){
            public boolean error = false;
            public String message = "User created successfully";
        });
    }
}

