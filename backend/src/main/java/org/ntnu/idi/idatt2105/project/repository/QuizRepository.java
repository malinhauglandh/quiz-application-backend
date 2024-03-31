package org.ntnu.idi.idatt2105.project.repository;

import org.ntnu.idi.idatt2105.project.entity.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for the Quiz entity.
 */
public interface QuizRepository extends JpaRepository<Quiz, Long> {}
