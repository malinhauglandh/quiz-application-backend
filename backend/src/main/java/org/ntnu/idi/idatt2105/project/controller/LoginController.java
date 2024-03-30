package org.ntnu.idi.idatt2105.project.controller;

import lombok.Value;
import org.ntnu.idi.idatt2105.project.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @Value("${jwt.secret}")
    private String secretKey;

    @GetMapping("/")
    public String docker() {
        return "Hello, World!";
    }
}