package org.ntnu.idi.idatt2105.project.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.ntnu.idi.idatt2105.project.model.UserLogin;
import org.ntnu.idi.idatt2105.project.service.TokenService;
import org.ntnu.idi.idatt2105.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
class UserControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private UserService userService;

  @MockBean
  private TokenService tokenService;

  @BeforeEach
  void setUp() {
    Map<String, String> tokens = new HashMap<>();
    tokens.put("accessToken", "access-token");
    tokens.put("refreshToken", "refresh-token");

    when(userService.createUser(any(UserLogin.class))).thenReturn(true);
    when(userService.login(any(UserLogin.class))).thenReturn(tokens);
    when(tokenService.refreshToken(any())).thenReturn("new-access-token");
  }
}