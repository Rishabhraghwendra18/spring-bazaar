package com.springbazaar.api_gateway;

import com.springbazaar.api_gateway.filter.JwtTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiGatewayConfig {

    private final JwtTokenFilter jwtTokenFilter;

    @Autowired
    public ApiGatewayConfig(JwtTokenFilter jwtTokenFilter){
        this.jwtTokenFilter=jwtTokenFilter;
    }
    @Bean
    public RouteLocator gatewayRouter(RouteLocatorBuilder builder){
        return builder.routes()
                .route(p->p.path("/api/v1/user/**").uri("lb://user-service"))
                .route(p->p.path("/api/v1/products/**").uri("lb://product-service"))
                .route(p->p.path("/api/v1/inventory/**").filters(f-> f.filter(jwtTokenFilter)).uri("lb://inventory-service"))
                .route(p->p.path("/api/v1/order/**").filters(f-> f.filter(jwtTokenFilter)).uri("lb://order-service"))
                .route(p->p.path("/api/v1/review/**").filters(f-> f.filter(jwtTokenFilter)).uri("lb://review-service"))
                .build();
    }
}
