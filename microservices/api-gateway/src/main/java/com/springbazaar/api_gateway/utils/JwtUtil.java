package com.springbazaar.api_gateway.utils;

import com.springbazaar.api_gateway.exceptions.ApplicationException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {

    @Value("${jwt.secret.key}")
    private String SECRET_KEY;
    public final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;

    //retrieve expiration date from jwt token
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }
    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }
    //for retrieveing any information from token we will need the secret key
    public Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }
    //check if the token has expired
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }
    public String getSubjectFromToken(String token){
        try{
            token=getTokenFromRequest(token);
            var claims = getAllClaimsFromToken(token);
            return claims.getSubject();
        }
        catch (JwtException e){
            throw new ApplicationException(HttpStatus.UNAUTHORIZED.value(),e.getMessage());
        }
    }
    //validate token
    public Boolean validateToken(String token) {
        return !isTokenExpired(token);
    }
    public String getTokenFromRequest(String token){
        if (token == null) return null;
        if(token.startsWith("Bearer ")){
            return token.substring(7);
        }
        return null;
    }
}