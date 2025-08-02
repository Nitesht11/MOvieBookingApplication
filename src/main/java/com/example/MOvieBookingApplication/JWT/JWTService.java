package com.example.MOvieBookingApplication.JWT;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;



import com.example.MOvieBookingApplication.Entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;


@Component
public class JWTService {

  @Value(("${jwt.secret}"))
  private String secretKey;

  @Value(("${jwt.expiration}"))
  private Long jwtExpiration;


  public String extractUsername(String jwtToken) {

    return extractClaims(jwtToken, Claims::getSubject);
  }
 private  <T>T  extractClaims(String jwtToken, Function<Claims,T> claimResolver){
    final Claims   claims =extractAllClaims(jwtToken);
    return  claimResolver.apply(claims);
  }

  private Claims extractAllClaims(String jwtToken) {
    return Jwts.parser()
            .verifyWith(getSignInKey())
            .build()
            .parseSignedClaims(jwtToken)
            .getPayload();
  }

  private SecretKey getSignInKey() {
    return Keys.hmacShaKeyFor(secretKey.getBytes());
  }

  public String generateToken(UserDetails userDetails) {
    return generateToken(new HashMap<>(), userDetails);
  }

  private String generateToken(HashMap<String, Object> extraClaims, UserDetails userDetails) {
    return Jwts.builder()
            .claims(extraClaims)
            .subject((userDetails.getUsername()))
            .issuedAt(new Date(System.currentTimeMillis()))
            .expiration(new Date(System.currentTimeMillis() + jwtExpiration))
            .signWith(getSignInKey())
            .compact();
  }

  public boolean isTokenValid(String jwtToken, UserDetails userDetails) {
    final String username = extractUsername(jwtToken);
    return (userDetails.getUsername().equals(username) && !isTokenExpired(jwtToken));
  }

  private boolean isTokenExpired(String jwtToken) {
    return extractExpiration(jwtToken).before(new Date());
  }

  private Date extractExpiration(String jwtToken){
    return extractClaims(jwtToken, Claims::getExpiration);
}
}
