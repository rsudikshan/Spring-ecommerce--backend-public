package com.sr.mobile_backend.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.io.Decoders;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.util.*;
import java.util.function.Function;

@Service
public class JwtService {
    String key;

    JwtService(){
        try {
            KeyGenerator generator = KeyGenerator.getInstance("HmacSHA256");
            SecretKey secretKey = generator.generateKey();
            key = Base64.getEncoder().encodeToString(secretKey.getEncoded());

        }
        catch (Exception e){
            System.out.println("cat");
        }
    }

    public SecretKey getKey(){
        byte[] keyBytes = Decoders.BASE64.decode(key);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(String username){
        Map<String, Objects> claims = new HashMap<>();


        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*60))
                .signWith(getKey())
                .compact();
    }

    public String getUsername(String token){
        return getClaim(token,Claims::getSubject);
    }

    public Date getExpiration(String token){
        return getClaim(token,Claims::getExpiration);
    }

    public Date getSignedAt(String token){
        return getClaim(token,Claims::getIssuedAt);
    }

    public Boolean isExpired(String token){

        return getExpiration(token).before(new Date());
    }

    public boolean validateToken(UserDetails userDetails, String token){

        if(isExpired(token)){
            return false;
        }
        return getUsername(token).equals(userDetails.getUsername());
    }


    public <T> T getClaim(String token, Function<Claims,T> claimsResolver){

        return claimsResolver.apply(getAllClaims(token));


    }

    public Claims getAllClaims(String token){

        return Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

    }
}
