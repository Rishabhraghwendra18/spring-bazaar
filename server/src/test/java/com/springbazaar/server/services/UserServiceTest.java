package com.springbazaar.server.services;

import com.springbazaar.server.entities.UsersEntity;
import com.springbazaar.server.exceptionHandlers.ApplicationException;
import com.springbazaar.server.repository.UserRepository;
import com.springbazaar.server.requestresponse.LoginRequest;
import com.springbazaar.server.utils.JwtUtil;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;
    private UserService userServiceTest;

    @BeforeEach
    void setUp(){
        this.userServiceTest = new UserService(userRepository,jwtUtil,passwordEncoder);
    }
    @Test
    public void testCreateUser_Success() {
        UsersEntity newUser = new UsersEntity();
        newUser.setEmail("test@example.com");
        newUser.setPassword("password123");
        UsersEntity savedUser = new UsersEntity();
        savedUser.setEmail("test@example.com");
        savedUser.setPassword("encodedPassword");

        when(userRepository.findById(newUser.getEmail())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(newUser.getPassword())).thenReturn("encodedPassword");
        when(userRepository.save(newUser)).thenReturn(savedUser);

        UsersEntity result = userServiceTest.createUser(newUser);

        assertNotNull(result);
        assertEquals("test@example.com", result.getEmail());
        assertEquals("encodedPassword", result.getPassword());
        verify(userRepository, times(1)).save(newUser);
    }

    @Test
    public void testCreateUser_UserAlreadyExists() {
        UsersEntity existingUser = new UsersEntity();
        existingUser.setEmail("test@example.com");

        when(userRepository.findById(existingUser.getEmail())).thenReturn(Optional.of(existingUser));

        ApplicationException exception = assertThrows(ApplicationException.class, () -> {
            userServiceTest.createUser(existingUser);
        });

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), exception.getErrorCode());
        assertEquals("User already exists!", exception.getMessage());
        verify(userRepository, never()).save(existingUser);
    }

    @Test
    public void testLogin_Success() {
        LoginRequest loginRequest = new LoginRequest("test@example.com","password123");

        UsersEntity user = new UsersEntity();
        user.setEmail("test@example.com");
        user.setPassword("encodedPassword");

        when(userRepository.findById(loginRequest.getEmail())).thenReturn(Optional.of(user));
        when(jwtUtil.generateToken(user)).thenReturn("jwtToken");

        String token = userServiceTest.login(loginRequest);

        assertEquals("jwtToken", token);
        verify(jwtUtil, times(1)).generateToken(user);
    }

    @Test
    public void testLogin_UserNotFound() {
        LoginRequest loginRequest = new LoginRequest("test@example.com","password123");

        when(userRepository.findById(loginRequest.getEmail())).thenReturn(Optional.empty());

        ApplicationException exception = assertThrows(ApplicationException.class, () -> {
            userServiceTest.login(loginRequest);
        });

        assertEquals(HttpStatus.NOT_FOUND.value(), exception.getErrorCode());
        assertEquals("User not found", exception.getMessage());
        verify(jwtUtil, never()).generateToken(any());
    }

}