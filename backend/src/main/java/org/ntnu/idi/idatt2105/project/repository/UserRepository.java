package org.ntnu.idi.idatt2105.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.ntnu.idi.idatt2105.project.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}