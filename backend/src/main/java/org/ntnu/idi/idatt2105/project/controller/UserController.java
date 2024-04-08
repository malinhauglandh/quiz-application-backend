package org.ntnu.idi.idatt2105.project.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Map;
import org.ntnu.idi.idatt2105.project.dto.user.UserCreationDTO;
import org.ntnu.idi.idatt2105.project.dto.user.UserLoginDTO;
import org.ntnu.idi.idatt2105.project.entity.user.User;
import org.ntnu.idi.idatt2105.project.exception.InvalidCredentialsException;
import org.ntnu.idi.idatt2105.project.exception.InvalidTokenException;
import org.ntnu.idi.idatt2105.project.mapper.user.UserMapper;
import org.ntnu.idi.idatt2105.project.service.user.TokenService;
import org.ntnu.idi.idatt2105.project.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/** Controller for handling login and user creation. */
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
@Tag(name = "User Management", description = "Endpoints for managing users")
public class UserController {

    /** The service class for the user controller. */
    private final UserService userService;

    /** The service class for the token controller. */
    private final TokenService tokenService;

    private final UserMapper userMapper;

    /**
     * Creates a new user controller with the specified services.
     *
     * @param userService userService
     * @param tokenService tokenService
     * @param userMapper userMapper
     */
    @Autowired
    public UserController(
            UserService userService, TokenService tokenService, UserMapper userMapper) {
        this.userService = userService;
        this.tokenService = tokenService;
        this.userMapper = userMapper;
    }

    /**
     * Endpoint for creating a new user.
     *
     * @param userCreationDTO The user login information
     * @return ResponseEntity with status 200 if the user was created, or status 409 if the username
     *     is already in use
     */
    @Operation(
            summary = "Create a new user",
            parameters = {
                @Parameter(name = "username", description = "The username of the user"),
                @Parameter(name = "password", description = "The password of the user")
            },
            responses = {
                @ApiResponse(responseCode = "200", description = "User created"),
                @ApiResponse(responseCode = "409", description = "Username already in use")
            })
    @PostMapping("/createUser")
    @ResponseBody
    public ResponseEntity<?> createUser(@RequestBody UserCreationDTO userCreationDTO) {
        User user = userMapper.toNewUser(userCreationDTO);
        if (userService.createUser(user)) {
            Map<String, String> tokens = tokenService.fetchTokens(user.getUsername());
            tokens.put("userId", String.valueOf(userService.findIdByUsername(user.getUsername())));
            return ResponseEntity.ok(tokens);
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Username or email already in use");
        }
    }

    /**
     * Endpoint for logging in a user.
     *
     * @param login The user login information
     * @return ResponseEntity with status 200 and a JWT token if the login was successful, or status
     *     401 if the login failed
     */
    @Operation(
            summary = "Login a user",
            parameters = {
                @Parameter(name = "username", description = "The username of the user"),
                @Parameter(name = "password", description = "The password of the user")
            },
            responses = {
                @ApiResponse(responseCode = "200", description = "User logged in"),
                @ApiResponse(responseCode = "401", description = "Login failed")
            })
    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<?> login(@RequestBody UserLoginDTO login) {
        User user = userMapper.toUser(login);
        try {
            Map<String, String> tokens = userService.login(user);
            return ResponseEntity.ok(tokens);
        } catch (InvalidCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Login failed: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while processing your request with cause\n" + e);
        }
    }

    /**
     * Endpoint for refreshing access token.
     *
     * @param request The HTTP request containing the refresh token
     * @return ResponseEntity with status 200 and a new access token if the refresh token is valid,
     *     or status 401 if the refresh token is invalid.
     */
    @Operation(
            summary = "Refresh access token",
            parameters = {@Parameter(name = "request", description = "The refresh token")},
            responses = {
                @ApiResponse(responseCode = "200", description = "Access token refreshed"),
                @ApiResponse(responseCode = "401", description = "Invalid or expired refresh token")
            })
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
