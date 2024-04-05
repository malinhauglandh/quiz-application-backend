package org.ntnu.idi.idatt2105.project.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.ntnu.idi.idatt2105.project.entity.User;
import org.ntnu.idi.idatt2105.project.exception.InvalidTokenException;
import org.ntnu.idi.idatt2105.project.model.UserLogin;
import org.ntnu.idi.idatt2105.project.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

  @Mock
  private UserRepository userRepository;

  @Mock
  private PasswordEncoder passwordEncoder;

  @Mock
  private TokenService tokenService;

  @InjectMocks
  private UserService userService;

  private User user;
  private UserLogin login;

  @BeforeEach
  void setUp() {
    login = new UserLogin("testUser", "password", "user@test.com");
    user = new User();
    user.setUsername(login.getUsername());
    user.setPassword(passwordEncoder.encode(login.getPassword()));
    user.setEmail(login.getEmail());
  }

  @Test
  void createUserSuccess() {
    when(userRepository.save(any(User.class))).thenReturn(user);
    boolean result = userService.createUser(login);
    assertTrue(result);
    verify(userRepository).save(any(User.class));
  }

  @Test
  void loginSuccess() {
    String rawPassword = "password";
    String encodedPassword = "encodedPassword";
    User userWithEncodedPassword = new User();
    userWithEncodedPassword.setUsername(login.getUsername());
    userWithEncodedPassword.setPassword(encodedPassword);
    userWithEncodedPassword.setEmail(login.getEmail());

    when(userRepository.findByUsername(login.getUsername())).thenReturn(Optional.of(userWithEncodedPassword));

    when(passwordEncoder.matches(eq(rawPassword), eq(encodedPassword))).thenReturn(true);

    when(tokenService.generateAccessToken(login.getUsername())).thenReturn("accessToken");
    when(tokenService.generateRefreshToken(login.getUsername())).thenReturn("refreshToken");

    assertDoesNotThrow(() -> {
      userService.login(login);
    });

    verify(tokenService).generateAccessToken(login.getUsername());
    verify(tokenService).generateRefreshToken(login.getUsername());
  }

  @Test
  void loginFailureInvalidPassword() {
    String rawPassword = "password";
    String encodedPassword = "encodedPassword";
    User userWithEncodedPassword = new User();
    userWithEncodedPassword.setUsername(login.getUsername());
    userWithEncodedPassword.setPassword(encodedPassword);
    userWithEncodedPassword.setEmail(login.getEmail());

    when(userRepository.findByUsername(login.getUsername())).thenReturn(Optional.of(userWithEncodedPassword));

    when(passwordEncoder.matches(eq(rawPassword), eq(encodedPassword))).thenReturn(false);

    Exception exception = assertThrows(InvalidTokenException.class, () -> {
      userService.login(login);
    });

    assertTrue(exception.getMessage().contains("Invalid username/password combination"));
  }

  @Test
  void loginFailureUserNotFound() {
    when(userRepository.findByUsername(login.getUsername())).thenReturn(Optional.empty());

    Exception exception = assertThrows(RuntimeException.class, () -> {
      userService.login(login);
    });

    assertTrue(exception.getMessage().contains("User not found"));
  }
}
