package org.ntnu.idi.idatt2105.project.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.ntnu.idi.idatt2105.project.dto.user.UserCreationDTO;
import org.ntnu.idi.idatt2105.project.dto.user.UserLoginDTO;
import org.ntnu.idi.idatt2105.project.entity.user.User;
import org.ntnu.idi.idatt2105.project.exception.InvalidTokenException;
import org.ntnu.idi.idatt2105.project.mapper.user.UserMapper;
import org.ntnu.idi.idatt2105.project.service.user.TokenService;
import org.ntnu.idi.idatt2105.project.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.core.StringContains.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
@WebMvcTest(UserController.class)
@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private TokenService tokenService;

    @MockBean
    private UserMapper userMapper;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    public void setup(WebApplicationContext webApplicationContext) {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void verifyCreateUserSuccess() throws Exception {
        UserCreationDTO userCreationDTO = new UserCreationDTO("testUser", "password", "user@test.com");
        User user = new User();
        user.setUsername(userCreationDTO.getUsername());
        user.setPassword(userCreationDTO.getPassword());
        user.setEmail(userCreationDTO.getEmail());

        when(userMapper.toNewUser(any(UserCreationDTO.class))).thenReturn(user);
        when(userService.createUser(any(User.class))).thenReturn(true);

        Map<String, String> tokens = new HashMap<>();
        tokens.put("accessToken", "access-token");
        tokens.put("userId", "1");

        when(tokenService.fetchTokens(anyString())).thenReturn(tokens);

        mockMvc.perform(post("/api/createUser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"username\": \"testUser\", \"password\": \"password\", \"email\": \"test@example.com\" }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").value("access-token"));
    }

    @Test
    public void verifyLoginSuccess() throws Exception {
        UserLoginDTO loginDTO = new UserLoginDTO("testUser", "password");
        User user = new User();
        user.setUsername(loginDTO.getUsername());
        user.setPassword(loginDTO.getPassword());

        Map<String, String> tokens = new HashMap<>();
        tokens.put("accessToken", "access-token");
        tokens.put("userId", "1");

        when(userMapper.toUser(any(UserLoginDTO.class))).thenReturn(user);
        when(userService.login(any(User.class))).thenReturn(tokens);

        mockMvc.perform(post("/api/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"username\": \"testUser\", \"password\": \"password\" }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").value("access-token"));
    }


    /*@Test
    public void loginFailureInvalidCredentials() throws Exception {
        when(userService.login(any(User.class))).thenThrow(new InvalidCredentialsException("Invalid username/password combination"));

        mockMvc.perform(post("/api/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"username\": \"wrongUser\", \"password\": \"wrongPassword\" }"))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string(containsString("Login failed: Invalid username/password combination")));
    }*/

    @Test
    public void verifyRefreshTokenSuccess() throws Exception {
        when(tokenService.refreshToken(any(HttpServletRequest.class))).thenReturn("new-access-token");

        mockMvc.perform(get("/api/refreshToken")
                        .header("Authorization", "Bearer expiredToken"))
                .andExpect(status().isOk())
                .andExpect(content().string("new-access-token"));
    }

    @Test
    public void refreshTokenFailureInvalidToken() throws Exception {
        when(tokenService.refreshToken(any(HttpServletRequest.class))).thenThrow(new InvalidTokenException("Invalid or expired refresh token"));

        mockMvc.perform(get("/api/refreshToken")
                        .header("Authorization", "Bearer invalidToken"))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string(containsString("Invalid or expired refresh token")));
    }
}
