package nl.fontys.s3.officereservationsystem.configuration.security.token.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import nl.fontys.s3.officereservationsystem.configuration.security.token.AccessToken;
import nl.fontys.s3.officereservationsystem.configuration.security.token.AccessTokenDecoder;
import nl.fontys.s3.officereservationsystem.configuration.security.token.AccessTokenEncoder;
import nl.fontys.s3.officereservationsystem.configuration.security.token.exception.InvalidAccessTokenException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class AccessTokenEncoderDecoderImpl implements AccessTokenEncoder, AccessTokenDecoder {
    private final Key key;

    public AccessTokenEncoderDecoderImpl(@Value("${jwt.secret}") String secretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    @Override
    public String encode(AccessToken accessToken) {
        Map<String, Object> claimsMap = new HashMap<>();
        if (accessToken.getUserId() != null) {
            claimsMap.put("userId", accessToken.getUserId());
        }
        if (!CollectionUtils.isEmpty(accessToken.getRoles())) {
            claimsMap.put("roles", accessToken.getRoles());
        }


        Instant now = Instant.now();
        return Jwts.builder()
                .setSubject(accessToken.getSubject())
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plus(30, ChronoUnit.MINUTES)))
                .addClaims(claimsMap)
                .signWith(key)
                .compact();
    }

    @Override
    public AccessToken decode(String accessTokenEncoded) {
        try {
            Jwt<?, Claims> jwt = Jwts.parserBuilder().setSigningKey(key).build()
                    .parseClaimsJws(accessTokenEncoded);
            Claims claims = jwt.getBody();

            // Retrieve the "roles" claim as a raw list and process it using the helper method
            List<?> rawRoles = claims.get("roles", List.class);
            List<String> roles = getStrings(rawRoles);

            Long userId = claims.get("userId", Long.class);

            return new AccessTokenImpl(claims.getSubject(), userId, roles);
        } catch (JwtException e) {
            throw new InvalidAccessTokenException(e.getMessage());
        }
    }

    private static List<String> getStrings(List<?> rawRoles) {
        List<String> roles = new ArrayList<>();

        // Check if rawRoles is not null and iterate over it
        if (rawRoles != null) {
            for (Object role : rawRoles) {
                // Ensure each item in the list is a string
                if (role instanceof String stringRole) {
                    roles.add(stringRole);
                } else {
                    // If any item is not a string, throw an exception
                    throw new ClassCastException("Expected a String but found " + role.getClass().getName());
                }
            }
        }
        return roles;
    }
}