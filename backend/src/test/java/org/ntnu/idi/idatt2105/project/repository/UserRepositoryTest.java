package org.ntnu.idi.idatt2105.project.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.ntnu.idi.idatt2105.project.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.ntnu.idi.idatt2105.project.entity.user.User;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void verifyFindByUsernameReturnUser() {
        User user = new User();
        user.setUsername("testUser");
        user.setPassword("password");
        user.setEmail("test@user.com");
        userRepository.save(user);

        Optional<User> foundUser = userRepository.findByUsername(user.getUsername());

        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getUsername()).isEqualTo(user.getUsername());
    }

    @Test
    public void verifyFindByUsernameWithNonExistingUserReturnEmpty() {
        Optional<User> foundUser = userRepository.findByUsername("nonExistingUser");

        assertThat(foundUser).isNotPresent();
    }
}
