package com.springbazaar.server.controllers;

import com.springbazaar.server.entities.UsersEntity;
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
    @GetMapping("/")
    public String testing(){return "testing";}
    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@Valid @RequestBody LoginRequest loginRequest, HttpServletResponse clientResponse){
        var jwtToken = userService.login(loginRequest);
        if (jwtToken != null){
            Cookie cookie = new Cookie("Authorization",jwtToken);
            cookie.setPath("/");
            cookie.setDomain("localhost");
            cookie.setSecure(false);
            clientResponse.addCookie(cookie);
            JwtResponse jwtResponse = new JwtResponse(jwtToken,loginRequest.getEmail(),"Login successfull",false,jwtUtil.getExpirationDateFromToken(jwtToken));
            return new ResponseEntity<>(jwtResponse,HttpStatus.OK);
        }
        JwtResponse jwtResponse = new JwtResponse(null,loginRequest.getEmail(),"Error while loggin in",true,null);
        return new ResponseEntity<>(jwtResponse,HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @PostMapping("/register")
    public ResponseEntity<Object> register(@Valid @RequestBody UsersEntity user){
        var res = userService.createUser(user);
        if(res == null){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new Object(){
                public boolean error = true;
                public String message = "User already exists";
            });
        }
        return ResponseEntity.status(HttpStatus.OK).body(new Object(){
            public boolean error = false;
            public String message = "User created successfully";
        });
    }
}
