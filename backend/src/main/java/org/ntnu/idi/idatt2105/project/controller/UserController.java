package org.ntnu.idi.idatt2105.project.controller;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Map;
import org.ntnu.idi.idatt2105.project.exception.InvalidTokenException;
import org.ntnu.idi.idatt2105.project.model.UserLogin;
import org.ntnu.idi.idatt2105.project.service.TokenService;
import org.ntnu.idi.idatt2105.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/** Controller for handling login and user creation. */
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService userService;

    private final TokenService tokenService;

    @Autowired
    public UserController(UserService userService, TokenService tokenService) {
        this.userService = userService;
        this.tokenService = tokenService;
    }

    /**
     * Endpoint for creating a new user.
     *
     * @param login The user login information
     * @return ResponseEntity with status 200 if the user was created, or status 409 if the username
     *     is already in use
     */
    @PostMapping("/createUser")
    public ResponseEntity<?> createUser(@RequestBody UserLogin login) {
        if (userService.createUser(login)) {
            Map<String, String> tokens = userService.login(login);
            return ResponseEntity.ok(tokens);
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already in use");
        }
    }

    /**
     * Endpoint for logging in a user.
     *
     * @param login The user login information
     * @return ResponseEntity with status 200 and a JWT token if the login was successful, or status
     *     401 if the login failed
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLogin login) {
        try {
            Map<String, String> tokens = userService.login(login);
            return ResponseEntity.ok(tokens);
        } catch (InvalidTokenException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Login failed: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while processing your request.");
        }
    }

    /**
     * Endpoint for refreshing access token.
     *
     * @param request The HTTP request containing the refresh token
     * @return ResponseEntity with status 200 and a new access token if the refresh token is valid,
     *     or status 401 if the refresh token is invalid.
     */
    @GetMapping("/refreshToken")
    public ResponseEntity<?> refreshToken(HttpServletRequest request) {
        try {
            String newToken = tokenService.refreshToken(request);
            return ResponseEntity.ok(newToken);
        } catch (InvalidTokenException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid or expired refresh token");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while processing your request.");
        }
    }
}
