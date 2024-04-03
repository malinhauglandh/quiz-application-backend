package org.ntnu.idi.idatt2105.project.controller;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Map;

import org.ntnu.idi.idatt2105.project.dto.user.UserCreationDTO;
import org.ntnu.idi.idatt2105.project.dto.user.UserLoginDTO;
import org.ntnu.idi.idatt2105.project.entity.User;
import org.ntnu.idi.idatt2105.project.exception.InvalidCredentialsException;
import org.ntnu.idi.idatt2105.project.exception.InvalidTokenException;
import org.ntnu.idi.idatt2105.project.mapper.UserMapper;
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

    private final UserMapper userMapper;

    @Autowired
    public UserController(UserService userService, TokenService tokenService, UserMapper userMapper) {
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
    @PostMapping("/createUser")
    @ResponseBody
    public ResponseEntity<?> createUser(@RequestBody UserCreationDTO userCreationDTO) {
        User user = userMapper.toNewUser(userCreationDTO);
        if (userService.createUser(user)) {
            Map<String, String> tokens = tokenService.fetchTokens(user.getUsername());
            tokens.put("userId", String.valueOf(userService.findIdByUsername(user.getUsername())));
            return ResponseEntity.ok(tokens);
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Username or email already in use");
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
