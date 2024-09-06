package com.springbazaar.user_service.services;

import com.springbazaar.user_service.entities.UsersEntity;
import com.springbazaar.user_service.exceptionHandlers.ApplicationException;
import com.springbazaar.user_service.requestResponseModels.LoginRequest;
import com.springbazaar.user_service.respository.UserRepository;
import com.springbazaar.user_service.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private PasswordEncoder passwordEncoder;
    @Autowired
    public UserService(UserRepository userRepository, JwtUtil jwtUtil, PasswordEncoder passwordEncoder){
        this.userRepository=userRepository;
        this.jwtUtil=jwtUtil;
        this.passwordEncoder=passwordEncoder;
    }
    public UsersEntity createUser(UsersEntity usersEntity){
        var user = userRepository.findById(usersEntity.getEmail());
        if(user.isPresent()){
            throw  new ApplicationException(HttpStatus.INTERNAL_SERVER_ERROR.value(), "User already exists!");
        }
        usersEntity.setPassword(passwordEncoder.encode(usersEntity.getPassword()));
        return userRepository.save(usersEntity);
    }
    public String login(LoginRequest request){
        Optional<UsersEntity> userOptional = userRepository.findById(request.getEmail());
        UsersEntity user = userOptional.orElseThrow(()-> new ApplicationException(HttpStatus.NOT_FOUND.value(), "User not found"));
        boolean isPasswordCorrect = passwordEncoder.matches(request.getPassword(), user.getPassword());
        if (!isPasswordCorrect){
            throw new ApplicationException(HttpStatus.UNAUTHORIZED.value(), "Password is incorrect");
        }
        return jwtUtil.generateToken(user);
    }
}

