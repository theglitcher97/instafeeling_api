package com.instafeeling.web.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component
@AllArgsConstructor
@NoArgsConstructor
public class JwtUtils {
    @Value("${jwt.secret}")
    private String JWT_SECRET;

    @Value("${jwt.issuer}")
    private String JWT_ISSUER;

    private static final Date DEFAULT_JWT_EXP_TIME = new Date(System.currentTimeMillis() + TimeUnit.HOURS.toMillis(1));

    public String createToken(Long userId){
        return  JWT.create()
                .withSubject(userId.toString())
                .withExpiresAt(DEFAULT_JWT_EXP_TIME)
                .withIssuer(JWT_ISSUER)
                .sign(getAlgorithm());
    }

    public Boolean validateJWT(String jwt){
        try {
            JWT.require(getAlgorithm()).withIssuer(JWT_ISSUER).build().verify(jwt);
            return true;
        } catch (JWTVerificationException e) {
            System.out.println("TOKEN VALIDATION FAILED!");
            return false;
        }
    }

    public String getSubject(String jwt){
        try {
            return JWT.require(getAlgorithm())
                    .withIssuer(JWT_ISSUER)
                    .build()
                    .verify(jwt)
                    .getSubject();
        } catch (JWTVerificationException e) {
            throw new RuntimeException("TOKEN VALIDATION FAILED!");
        }
    }

    private Algorithm getAlgorithm() {
        return Algorithm.HMAC256(JWT_SECRET);
    }
}
