package com.example.MOvieBookingApplication.JWT;



import com.example.MOvieBookingApplication.Entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

@Component
public class JWTService {

  @Value(("${jwt.secret}"))
  private String SecretKey;

  @Value(("${jwt."))
  private Long jwtexpiration;

  public String extractUsername(String jwtToken) {

    return extractClaims(jwtToken, Claims:: getSubject);
  }



  final Claims Claims = extractClaims(String jwtToken){
    return extactClaims
  }

  private String extractAllClaims(String jwtToken) {
    return Jwts.parser()
            .verifyWith(getSignInKey())
            .build()
            .parsedSignedClaims(jwtToken)
            .getPayLoad();
  }
   public  SecretKey getSignInKey {
    return Keys.hmacShaKeyFor(secretKey.getBytes());
  }


  public String generateToken(UserDetails userDetails) {
  }
}
