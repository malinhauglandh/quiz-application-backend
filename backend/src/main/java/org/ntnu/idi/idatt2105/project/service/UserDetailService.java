package org.ntnu.idi.idatt2105.project.service;

import static org.springframework.security.core.userdetails.User.withUsername;

import org.ntnu.idi.idatt2105.project.entity.User;
import org.ntnu.idi.idatt2105.project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/** Service for loading user information. */
@Service
public class UserDetailService implements UserDetailsService {

    /**
     * The user repository
     */
    private final UserRepository userRepository; // Assume you have a UserRepository

    /**
     * Constructor for UserDetailService
     * @param userRepository The user repository
     */
    @Autowired
    public UserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Load user information by username.
     *
     * @param username The username to load
     * @return UserDetails The user details for the given username
     * @throws UsernameNotFoundException If the user is not found
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user =
                userRepository
                        .findByUsername(username)
                        .orElseThrow(
                                () ->
                                        new UsernameNotFoundException(
                                                "User not found with username: " + username));

        return withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities("USER")
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .build();
    }
}
