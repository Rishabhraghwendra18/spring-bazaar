package com.springbazaar.api_gateway.filter;

import com.springbazaar.api_gateway.exceptions.ApplicationException;
import com.springbazaar.api_gateway.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

//@Component
//public class JwtTokenFilter implements GatewayFilter{
//
//    @Override
//    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//        System.out.println("hello");
//        return null;
//    }
//}
@Component
public class JwtTokenFilter implements GatewayFilter {

    private final JwtUtil jwtUtil;

    @Autowired
    public JwtTokenFilter(JwtUtil jwtUtil){
        this.jwtUtil=jwtUtil;
    }


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        if (this.isAuthMissing(request)) {
            throw new ApplicationException(HttpStatus.UNAUTHORIZED.value(),"Authorization header missing");
        }

        String token = this.getAuthHeader(request);
        token = jwtUtil.getTokenFromRequest(token);
        if (!jwtUtil.validateToken(token)) {
            throw new ApplicationException(HttpStatus.FORBIDDEN.value(),"JWT token invalid");
        }

        return chain.filter(exchange);
    }

    private String getAuthHeader(ServerHttpRequest request) {
        return request.getHeaders().getOrEmpty("Authorization").getFirst();
    }

    private boolean isAuthMissing(ServerHttpRequest request) {
        return !request.getHeaders().containsKey("Authorization");
    }

}
