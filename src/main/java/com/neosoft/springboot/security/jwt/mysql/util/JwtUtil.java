package com.neosoft.springboot.security.jwt.mysql.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component
public class JwtUtil {

    @Value("${app.secret}")
    private  String secret;


    //1. Generate Token
    public String generateToken(String subject){
        return Jwts.builder()
                .setSubject(subject)
                .setIssuer("NeoSoft")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(15)))
                .signWith(SignatureAlgorithm.HS512, secret.getBytes())
                .compact();
    }

    //2. Read Claims
    public Claims getClaims(String token){
        System.out.println("getClaims:");
        return Jwts.parser()
                .setSigningKey(secret.getBytes())
                .parseClaimsJws(token)
                .getBody();
    }

    //3. Reads Expiry Date
    public Date getExpDate(String token){
        return getClaims(token).getExpiration();
    }

    //4.Read Subject/username
    public String getUsername(String  token){
        System.out.println("getUsername:");
        return getClaims(token).getSubject();
    }

    //5. Validate expiry date
    public boolean isTokenExp(String token){
        Date expDate =  getExpDate(token);
        return expDate.before(new Date(System.currentTimeMillis()));
    }

//6.Validate user name in token and database, expDate
    public boolean validateToken(String token, String username){
        String usernmToken = getUsername(token);
        System.out.println("validateToken:"+usernmToken);
        return (username.equals(usernmToken) && !isTokenExp(token));
    }
}
