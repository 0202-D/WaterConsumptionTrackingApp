package io.ylab.petrov.security;

import io.jsonwebtoken.*;
import io.ylab.petrov.dto.user.UserResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
@Component
public class JwtProvider {
    private static final byte [] SECRET_KEY = "mckkjnnhT53=pcntUUtwb34Zzhhenfn".getBytes(StandardCharsets.UTF_8);
    private static final Logger logger = LoggerFactory.getLogger(JwtProvider.class);
    private static byte[] setSigningKey(byte[] secretKey) {
        return secretKey;
    }
    //сгенерировать токен JWT (Access Token)
    public String generateAccessJwtToken(UserResponseDto userPrincipal) {
        return Jwts.builder()
                .setSubject((userPrincipal.getUserName()))
                .setId(String.valueOf(userPrincipal.getUserId()))
                .setExpiration(Timestamp.from(Instant.now().plus(1, ChronoUnit.DAYS)))
                .signWith(
                        SignatureAlgorithm.HS512,
                        setSigningKey(SECRET_KEY)
                )
                .compact();
    }
    public byte[] setSigningKey(String base64EncodedKeyBytes) {
        Assert.hasText(base64EncodedKeyBytes, "signing key cannot be null or empty.");
        return Base64.getDecoder().decode(base64EncodedKeyBytes);
    }
    public boolean validateJwtToken(HttpServletRequest req) {
        String authorizationHeader = req.getHeader("Authorization");

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);

            try {
                Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
                return true;
            } catch (MalformedJwtException e) {
                logger.error("Invalid JWT token -> Message: {}", e);
            } catch (UnsupportedJwtException e) {
                logger.error("Unsupported JWT token -> Message: {}", e);
            } catch (IllegalArgumentException e) {
                logger.error("JWT claims string is empty -> Message: {}", e);
            }
        }

        return false;
    }
    public Long getPrincipalId(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(setSigningKey(SECRET_KEY))
                .parseClaimsJws(token)
                .getBody();

        return Long.parseLong(claims.getId());
    }

}
