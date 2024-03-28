package org.ntnu.idi.idatt2105.project.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @GetMapping("/")
    public String docker() {
        return "Hello, World!";
    }
}