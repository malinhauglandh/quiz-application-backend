package org.ntnu.idi.idatt2105.project.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.ntnu.idi.idatt2105.project.service.user.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

/** Filter for handling JWT authorization. */
public class JWTAuthorizationFilter extends OncePerRequestFilter {

    /** The token service. */
    private final TokenService tokenService;

    /**
     * Creates a new JWT authorization filter with the specified token service.
     *
     * @param tokenService The token service
     */
    @Autowired
    public JWTAuthorizationFilter(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    /**
     * Filter for handling JWT authorization.
     *
     * @param request The HTTP request
     * @param response The HTTP response
     * @param filterChain The filter chain
     * @throws ServletException If an error occurs
     * @throws IOException If an error occurs
     */
    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String token = getTokenFromRequest(request);

        if (token != null && tokenService.validateToken(token)) {
            Authentication auth = tokenService.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(auth);
        }

        filterChain.doFilter(request, response);
    }

    /**
     * Get the token from the request.
     *
     * @param request The HTTP request
     * @return The token
     */
    private String getTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
