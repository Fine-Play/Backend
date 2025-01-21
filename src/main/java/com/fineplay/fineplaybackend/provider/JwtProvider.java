package com.fineplay.fineplaybackend.provider;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.security.Key;
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
        Key key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
        Date expiredTime = Date.from(Instant.now().plus(1, ChronoUnit.HOURS)); // 1시간
        return Jwts.builder()
                .signWith(key, SignatureAlgorithm.HS256)
                .setSubject(email) // email이 주체
                .setIssuedAt(new Date()) // 생성 시간
                .setExpiration(expiredTime) // 만료 시간
                .compact();
    }

    // JWT 검증 메서드
    public String validateJwt(String token) {

        try {
            Key key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)); // 동적으로 생성
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(key) // token을 파싱
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            return claims.getSubject(); // token을 파싱해서 토큰 생성할 때 넣은 email을 가져옴

        } catch (io.jsonwebtoken.ExpiredJwtException ex) {
            throw new RuntimeException("JWT has expired", ex);
        } catch (io.jsonwebtoken.SignatureException ex) {
            throw new RuntimeException("Invalid JWT signature", ex);
        } catch (Exception ex) {
            throw new RuntimeException("Invalid JWT token", ex);
        }
    }
}
