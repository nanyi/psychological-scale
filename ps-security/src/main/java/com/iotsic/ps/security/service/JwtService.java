package com.iotsic.ps.security.service;

import com.iotsic.ps.common.constant.SystemConstant;
import com.iotsic.ps.common.enums.ErrorCodeEnum;
import com.iotsic.ps.common.exception.BusinessException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class JwtService {

    @Value("${jwt.secret:#{T(com.iotsic.ps.common.constant.SystemConstant).TOKEN_SECRET}}")
    private String secret;

    @Value("${jwt.expire:#{T(com.iotsic.ps.common.constant.SystemConstant).TOKEN_EXPIRE_TIME}}")
    private Long expire;

    @Value("${jwt.refresh-expire:#{T(com.iotsic.ps.common.constant.SystemConstant).REFRESH_TOKEN_EXPIRE_TIME}}")
    private Long refreshExpire;

    @Value("${jwt.issuer:psychological-scale}")
    private String issuer;

    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(Long userId, String username, Map<String, Object> extraClaims) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userId);
        claims.put("username", username);
        if (extraClaims != null) {
            claims.putAll(extraClaims);
        }

        return Jwts.builder()
                .claims(claims)
                .subject(username)
                .issuer(issuer)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expire))
                .signWith(getSecretKey())
                .compact();
    }

    public String generateRefreshToken(Long userId, String username) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userId);
        claims.put("username", username);
        claims.put("type", "refresh");

        return Jwts.builder()
                .claims(claims)
                .subject(username)
                .issuer(issuer)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + refreshExpire))
                .signWith(getSecretKey())
                .compact();
    }

    public Claims parseToken(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(getSecretKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (ExpiredJwtException e) {
            log.error("Token expired: {}", e.getMessage());
            throw BusinessException.of(ErrorCodeEnum.TOKEN_EXPIRED.getCode(), "Token已过期");
        } catch (JwtException e) {
            log.error("Token invalid: {}", e.getMessage());
            throw BusinessException.of(ErrorCodeEnum.TOKEN_INVALID.getCode(), "Token无效");
        }
    }

    public Long getUserId(String token) {
        Claims claims = parseToken(token);
        return claims.get("userId", Long.class);
    }

    public String getUsername(String token) {
        Claims claims = parseToken(token);
        return claims.getSubject();
    }

    public boolean isTokenExpired(String token) {
        try {
            Claims claims = parseToken(token);
            return claims.getExpiration().before(new Date());
        } catch (Exception e) {
            return true;
        }
    }

    public boolean isRefreshToken(String token) {
        try {
            Claims claims = parseToken(token);
            return "refresh".equals(claims.get("type"));
        } catch (Exception e) {
            return false;
        }
    }

    public boolean validateToken(String token) {
        try {
            parseToken(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Long getExpire() {
        return expire;
    }
}
