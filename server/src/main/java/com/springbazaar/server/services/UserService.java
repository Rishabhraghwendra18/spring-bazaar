package com.springbazaar.server.services;

import com.springbazaar.server.entities.UsersEntity;
import com.springbazaar.server.exceptionHandlers.ApplicationException;
import com.springbazaar.server.repository.UserRepository;
import com.springbazaar.server.requestresponse.LoginRequest;
import com.springbazaar.server.utils.JwtUtil;
import com.springbazaar.server.utils.UserRole;
import jakarta.servlet.http.Cookie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    @Autowired
    public UserService(UserRepository userRepository, JwtUtil jwtUtil){

        this.userRepository=userRepository;
        this.jwtUtil=jwtUtil;
    }
    public UsersEntity createUser(UsersEntity usersEntity){
        var user = userRepository.findById(usersEntity.getEmail());
        return user.orElseThrow(()-> new ApplicationException(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Not able to create user"));
    }
    public String login(LoginRequest request){
        var user = userRepository.findById(request.getEmail());
        if (user.isPresent()){
            return jwtUtil.generateToken(user.get());
        }
        else{
            throw new ApplicationException(HttpStatus.NOT_FOUND.value(), "User not found");
        }
    }
}
