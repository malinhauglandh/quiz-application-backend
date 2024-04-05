package org.ntnu.idi.idatt2105.project.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.ntnu.idi.idatt2105.project.entity.user.User;
import org.ntnu.idi.idatt2105.project.exception.ExistingUserException;
import org.ntnu.idi.idatt2105.project.exception.InvalidCredentialsException;
import org.ntnu.idi.idatt2105.project.repository.user.UserRepository;
import org.ntnu.idi.idatt2105.project.service.user.TokenService;
import org.ntnu.idi.idatt2105.project.service.user.UserService;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private TokenService tokenService;

    @InjectMocks
    private UserService userService;

    private User validUser;

    @BeforeEach
    void setUp() {
        validUser = new User();
        validUser.setUsername("user");
        validUser.setPassword("password");
        validUser.setEmail("user@test.com");
    }

    @Test
    void verifyCreateUserSuccess() {
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");

        assertTrue(userService.createUser(validUser));

        verify(userRepository).save(validUser);
        assertEquals("encodedPassword", validUser.getPassword());
    }

    /*@Test
    void createUserWithAlreadyExistingUsername() {
        User newUser = new User("testUser", "testEmail@example.com", "password");

        String exceptionMessage = "Constraint violation exception ... Duplicate entry for username 'testUser' ...";
        when(userRepository.save(any(User.class)))
                .thenThrow(new DataIntegrityViolationException(exceptionMessage));

        ExistingUserException thrown = assertThrows(ExistingUserException.class,
                () -> userService.createUser(newUser),
                "Expected createUser to throw an ExistingUserException");

        assertNotNull(thrown.getMessage());
        assertTrue(thrown.getMessage().contains("Username already exists"));
    }*/


    @Test
    void verifyLoginSuccess() {
        User validUser = new User("testUser", "user@test.com", "password");
        validUser.setPassword("encodedPassword");

        Map<String, String> mockTokens = new HashMap<>();
        mockTokens.put("accessToken", "token");

        when(userRepository.findByUsername(validUser.getUsername())).thenReturn(Optional.of(validUser));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);
        when(tokenService.fetchTokens(validUser.getUsername())).thenReturn(mockTokens);

        Map<String, String> tokens = userService.login(validUser);

        assertNotNull(tokens, "The tokens map should not be null.");
        assertTrue(tokens.containsKey("accessToken"), "The tokens map should contain an access token.");
        assertEquals("token", tokens.get("accessToken"), "The access token should match the expected value.");
    }

    @Test
    void loginInvalidCredentials() {
        when(userRepository.findByUsername(validUser.getUsername())).thenReturn(Optional.of(validUser));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(false);

        InvalidCredentialsException thrown = assertThrows(InvalidCredentialsException.class, () -> userService.login(validUser));

        assertTrue(thrown.getMessage().contains("Invalid username/password combination"));
    }
}
