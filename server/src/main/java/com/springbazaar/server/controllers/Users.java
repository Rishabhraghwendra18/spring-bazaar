package com.springbazaar.server.controllers;

import com.springbazaar.server.entities.UsersEntity;
import com.springbazaar.server.exceptionHandlers.ApplicationException;
import com.springbazaar.server.requestresponse.JwtResponse;
import com.springbazaar.server.requestresponse.LoginRequest;
import com.springbazaar.server.services.UserService;
import com.springbazaar.server.utils.JwtUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class Users {
    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final DaoAuthenticationProvider authenticationProvider;
    @Autowired
    public Users(UserService userService, JwtUtil jwtUtil, DaoAuthenticationProvider authenticationProvider){

        this.userService=userService;
        this.jwtUtil=jwtUtil;
        this.authenticationProvider=authenticationProvider;
    }
    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@Valid @RequestBody LoginRequest loginRequest, HttpServletResponse clientResponse){
        authenticationProvider.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        var jwtToken = userService.login(loginRequest);
        Cookie cookie = new Cookie("Authorization",jwtToken);
        cookie.setPath("/");
        cookie.setDomain("localhost");
        cookie.setSecure(false);
        clientResponse.addCookie(cookie);
        JwtResponse jwtResponse = new JwtResponse(jwtToken,loginRequest.getEmail(),"Login successfull",false,jwtUtil.getExpirationDateFromToken(jwtToken));
        return new ResponseEntity<>(jwtResponse,HttpStatus.OK);
//        return null;
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
