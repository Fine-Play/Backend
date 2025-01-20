package com.fineplay.fineplaybackend.provider;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

// JWT 발급을 위한 provider
@Component
public class JwtProvider {

    @Value("${secret-key}")
    private String secretKey;

    // JWT 생성하는 메서드
    public String createJwt(String email) {
        Date expiredTime = Date.from(Instant.now().plus(1, ChronoUnit.HOURS)); // 1시간
        return Jwts.builder()
                .signWith(SignatureAlgorithm.ES256, secretKey) // HS256
                .setSubject(email) // email이 주체
                .setIssuedAt(new Date()) // 생성 시간
                .setExpiration(expiredTime) // 만료 시간
                .compact();
    }

    // JWT 검증 메서드
    public String validateJwt(String token) {
        Claims claims = null;

        try {
            claims = Jwts.parser()
                    .setSigningKey(secretKey) // token을 secretKey를 이용해 파싱
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }

        return claims.getSubject(); // token을 파싱해서 토큰 생성할 때 넣은 email을 가져옴
    }
}
