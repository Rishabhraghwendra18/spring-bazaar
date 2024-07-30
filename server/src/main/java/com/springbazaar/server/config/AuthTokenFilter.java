package com.springbazaar.server.config;

import com.springbazaar.server.entities.UsersEntity;
import com.springbazaar.server.repository.UserRepository;
import com.springbazaar.server.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class AuthTokenFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    @Autowired
    public AuthTokenFilter(JwtUtil jwtUtil, UserRepository userRepository){
        this.jwtUtil=jwtUtil;
        this.userRepository=userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String origin = request.getHeader(HttpHeaders.ORIGIN);
        response.setHeader("Access-Control-Allow-Origin", origin);
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With, remember-me, Authorization");
        // Handle preflight requests
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
            return;
        }
        String jwtToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        jwtToken = jwtUtil.getTokenFromRequest(jwtToken);
        if (jwtToken != null &&  jwtUtil.validateToken(jwtToken)){
            boolean isTokenValid = jwtUtil.validateToken(jwtToken);
            if(isTokenValid){
                Claims claims = jwtUtil.getAllClaimsFromToken(jwtToken);
                if (SecurityContextHolder.getContext().getAuthentication() ==  null){
                    List<GrantedAuthority> authorities = new ArrayList<>();
                    String emailId = claims.getSubject();
                    Optional<UsersEntity> user = userRepository.findById(emailId);
                    if (user.isPresent()){
                        authorities.add(new SimpleGrantedAuthority(user.get().getRole().toString()));
                        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(user.get(),null,authorities);
                        SecurityContextHolder.getContext().setAuthentication(auth);
                    }
                }
            }
        }
        filterChain.doFilter(request,response);
    }
}