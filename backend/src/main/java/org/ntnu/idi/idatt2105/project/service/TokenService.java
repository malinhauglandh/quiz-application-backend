package org.ntnu.idi.idatt2105.project.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
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

    @Value("${jwt.refresh.expiration.time}")
    private long refreshExpirationTime;

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
    public String generateToken(String subject, long expirationTime) {
        Date issuedAt = new Date();
        Date expiresAt = new Date(issuedAt.getTime() + expirationTime);

        return JWT.create()
                .withSubject(subject)
                .withIssuedAt(issuedAt)
                .withExpiresAt(expiresAt)
                .sign(Algorithm.HMAC512(secretKey));
    }

    /**
     * Generate an access token for a given subject (user identifier).
     *
     * @param subject The user identifier
     * @return A JWT access token
     */
    public String generateAccessToken(String subject) {
        return generateToken(subject, expirationTime);
    }

    /**
     * Generate a refresh token for a given subject (user identifier).
     *
     * @param subject The user identifier
     * @return A JWT refresh token
     */
    public String generateRefreshToken(String subject) {
        return generateToken(subject, refreshExpirationTime);
    }

    public Map<String, String> fetchTokens(String subject) {
        String accessToken = generateAccessToken(subject);
        String refreshToken = generateRefreshToken(subject);

        Map<String, String> tokens = new HashMap<>();
        tokens.put("accessToken", accessToken);
        tokens.put("refreshToken", refreshToken);

        return tokens;
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

    /**
     * Refresh a token.
     *
     * @param request The HTTP request
     * @return A new JWT token
     */
    public String refreshToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        String token = authorizationHeader.substring(7);
        String username = validateTokenAndGetSubject(token);
        return generateAccessToken(username);
    }
}
