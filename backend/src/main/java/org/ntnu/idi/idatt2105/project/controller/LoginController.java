package org.ntnu.idi.idatt2105.project.controller;

import org.ntnu.idi.idatt2105.project.model.UserLogin;
import org.ntnu.idi.idatt2105.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/** Controller for handling login and user creation. */
@RestController
public class LoginController {

    private final UserService userService;

    @Autowired
    public LoginController(UserService userService) {
        this.userService = userService;
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
            return ResponseEntity.ok().build();
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
        String token = userService.login(login);
        if (token != null) {
            return ResponseEntity.ok(token); // Return JWT token
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Login failed: Incorrect username or password.");
        }
    }
}
