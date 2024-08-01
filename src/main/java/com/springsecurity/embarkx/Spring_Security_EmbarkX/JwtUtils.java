package com.springsecurity.embarkx.Spring_Security_EmbarkX;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${spring.app.jwtSecret}")
    private String jwtSecret;

    @Value("${spring.app.jwtExpirationInMs}")
    private int jwtExpirationInMs;

    public String getJwtFromHeader(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        logger.debug("Authorization Header : {}", bearerToken);

        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public String generateJwtTokenFromUsername(UserDetails userDetails) {
        String userName = userDetails.getUsername();
        return Jwts.builder().subject(userName)
                .issuedAt(new Date())
                .expiration(new Date(new Date().getTime() + jwtExpirationInMs))
                .signWith(key())
                .compact();
    }

    public String getUserNameFromJwtToken(String jwtToken) {
        return Jwts.parser().verifyWith((SecretKey) key())
                .build().parseSignedClaims(jwtToken)
                .getPayload().getSubject();
    }

    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    public boolean validateJwtToken(String jwtToken) {
        try {
            logger.debug("Trying to validate the jwtToken");
            Jwts.parser()
                    .verifyWith((SecretKey) key())
                    .build().parseSignedClaims(jwtToken);
            return true;
        } catch (MalformedJwtException malformedJwtException) {
            logger.error("Invalid JWT Token : {}", malformedJwtException.getMessage());
        } catch (ExpiredJwtException expiredJwtException) {
            logger.error("Expired JWT Token : {}", expiredJwtException.getMessage());
        } catch (UnsupportedJwtException unsupportedJwtException) {
            logger.error("Unsupported JWT Token : {}", unsupportedJwtException.getMessage());
        } catch (IllegalArgumentException illegalArgumentException) {
            logger.error("Illegal argument passed : {}", illegalArgumentException.getMessage());
        }
        return false;
    }
}
