package org.ntnu.idi.idatt2105.project.service;

import org.hibernate.exception.ConstraintViolationException;
import org.ntnu.idi.idatt2105.project.entity.User;
import org.ntnu.idi.idatt2105.project.exception.ExistingUserException;
import org.ntnu.idi.idatt2105.project.exception.InvalidTokenException;
import org.ntnu.idi.idatt2105.project.model.UserLogin;
import org.ntnu.idi.idatt2105.project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    public UserService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            TokenService tokenService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
    }

    /**
     * Method for creating a new user. If the username or email is already in use, an
     * ExistingUserException is thrown.
     *
     * @param userLogin The user login information
     * @return true if the user was created
     */
    public boolean createUser(UserLogin userLogin) {
        try{
            String encodedPassword = passwordEncoder.encode(userLogin.getPassword());
            User newUser = new User();
            newUser.setUsername(userLogin.getUsername());
            newUser.setPassword(encodedPassword);
            newUser.setEmail(userLogin.getEmail());
            userRepository.save(newUser);
        } catch (Exception e) {
            if(e.getCause() instanceof ConstraintViolationException) {
                String constraintName = ((ConstraintViolationException) e.getCause()).getConstraintName();
                if("UK_user_email".equals(constraintName)) {
                    throw new ExistingUserException("Email already exists: " + userLogin.getEmail());
                } else if("UK_user_username".equals(constraintName)) {
                    throw new ExistingUserException("Username already exists: " + userLogin.getUsername());
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
    public String login(UserLogin userLogin) {
        User user =
                userRepository
                        .findByUsername(userLogin.getUsername())
                        .orElseThrow(() -> new InvalidTokenException("User not found"));

        if (passwordEncoder.matches(userLogin.getPassword(), user.getPassword())) {
            return tokenService.generateToken(user.getUsername());
        } else {
            throw new InvalidTokenException("Invalid username/password combination");
        }
    }
}
