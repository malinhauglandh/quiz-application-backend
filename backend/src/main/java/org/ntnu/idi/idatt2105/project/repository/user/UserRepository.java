package org.ntnu.idi.idatt2105.project.repository.user;

import java.util.Optional;
import org.ntnu.idi.idatt2105.project.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

/** Repository interface for the User entity. */
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
