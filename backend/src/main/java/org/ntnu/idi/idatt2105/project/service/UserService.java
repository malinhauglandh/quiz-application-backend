package org.ntnu.idi.idatt2105.project.service;

import java.util.Map;
import java.util.Optional;
import org.ntnu.idi.idatt2105.project.entity.User;
import org.ntnu.idi.idatt2105.project.exception.ExistingUserException;
import org.ntnu.idi.idatt2105.project.exception.InvalidCredentialsException;
import org.ntnu.idi.idatt2105.project.mapper.UserMapper;
import org.ntnu.idi.idatt2105.project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Service class for user related operations. Used by the LoginController to handle user creation
 * and login.
 */
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    private final UserMapper userMapper;

    @Autowired
    public UserService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            TokenService tokenService,
            UserMapper userMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
        this.userMapper = userMapper;
    }

    /**
     * Method for creating a new user. If the username or email is already in use, an
     * ExistingUserException is thrown.
     *
     * @param newUser The user login information
     * @return true if the user was created
     */
    public boolean createUser(User newUser) {
        try {
            if (newUser.getUsername() == null
                    || newUser.getPassword() == null
                    || newUser.getEmail() == null) {
                throw new InvalidCredentialsException("Username, password or email is null");
            }
            if (newUser.getUsername().isBlank()
                    || newUser.getPassword().isBlank()
                    || newUser.getEmail().isBlank()) {
                throw new InvalidCredentialsException("Username, password or email is blank");
            }
            String encodedPassword = passwordEncoder.encode(newUser.getPassword());
            newUser.setPassword(encodedPassword);
            userRepository.save(newUser);
        } catch (DataIntegrityViolationException e) {
            // Check if the exception message contains hints about the duplicate email or username
            if (e.getMessage() != null) {
                if (e.getMessage().contains("Duplicate entry")
                        && e.getMessage().contains("UK_user_email")) {
                    throw new ExistingUserException("Email already exists: " + newUser.getEmail());
                } else if (e.getMessage().contains("Duplicate entry")
                        && e.getMessage().contains("UK_user_username")) {
                    throw new ExistingUserException(
                            "Username already exists: " + newUser.getUsername());
                }
            }
            throw e;
        }
        return true;
    }

    /**
     * Method for logging in a user. If the user is found and the password is correct, a token is
     * generated. Otherwise, an InvalidTokenException is thrown.
     *
     * @param userLogin The user login information
     * @return A JWT token
     */
    public Map<String, String> login(User userLogin) {
        User user =
                userRepository
                        .findByUsername(userLogin.getUsername())
                        .orElseThrow(() -> new InvalidCredentialsException("User not found"));
        if (passwordEncoder.matches(userLogin.getPassword(), user.getPassword())) {
            Map<String, String> tokens = tokenService.fetchTokens(user.getUsername());
            tokens.put("userId", String.valueOf(user.getUserId()));
            return tokens;
        } else {
            throw new InvalidCredentialsException("Invalid username/password combination");
        }
    }

    public Optional<User> findUserById(Long userId) {
        return userRepository.findById(userId);
    }

    public int findIdByUsername(String username) {
        try {
            return userRepository.findByUsername(username).get().getUserId();
        } catch (Exception e) {
            throw new InvalidCredentialsException("User not found");
        }
    }
}
