package org.ntnu.idi.idatt2105.project.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import java.util.Date;
import org.ntnu.idi.idatt2105.project.exception.InvalidTokenException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

/** Service for handling JWT tokens. */
@Service
public class TokenService {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration.time}")
    private long expirationTime;

    private final UserDetailService userDetailService;

    public TokenService(UserDetailService userDetailService) {
        this.userDetailService = userDetailService;
    }

    /**
     * Generate a token for a given subject (user identifier)
     *
     * @param subject The user identifier
     * @return A JWT token
     */
    public String generateToken(String subject) {
        Date issuedAt = new Date();
        Date expiresAt = new Date(issuedAt.getTime() + expirationTime);

        return JWT.create()
                .withSubject(subject)
                .withIssuedAt(issuedAt)
                .withExpiresAt(expiresAt)
                .sign(Algorithm.HMAC512(secretKey));
    }

    /**
     * Validate a token and return the subject (user identifier) if the token is valid
     *
     * @param token The token to validate
     * @return The subject (user identifier) if the token is valid, otherwise null
     */
    public String validateTokenAndGetSubject(String token) {
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC512(secretKey)).build();
            DecodedJWT decodedJWT = verifier.verify(token);
            return decodedJWT.getSubject();
        } catch (JWTVerificationException exception) {
            throw new InvalidTokenException("The token is invalid or expired", exception);
        }
    }

    /**
     * Get authentication object from a valid token.
     *
     * @param token The JWT token
     * @return Authentication The Spring Security Authentication object
     */
    public Authentication getAuthentication(String token) {
        if (validateToken(token)) {
            String username = validateTokenAndGetSubject(token);
            UserDetails userDetails = userDetailService.loadUserByUsername(username);

            UsernamePasswordAuthenticationToken auth =
                    new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(auth);
            return auth;
        } else {
            throw new InvalidTokenException("Invalid JWT token");
        }
    }

    /**
     * Validate a token's integrity and expiry.
     *
     * @param token The token to validate
     * @return true if the token is valid, false otherwise
     */
    public boolean validateToken(String token) {
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC512(secretKey)).build();
            verifier.verify(token);
            return true;
        } catch (JWTVerificationException exception) {
            throw new InvalidTokenException("The token is invalid or expired", exception);
        }
    }
}
