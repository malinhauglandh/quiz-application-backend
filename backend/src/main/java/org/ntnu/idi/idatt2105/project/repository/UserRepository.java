package org.ntnu.idi.idatt2105.project.repository;

import java.util.Optional;
import org.ntnu.idi.idatt2105.project.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
