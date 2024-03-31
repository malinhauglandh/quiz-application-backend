package org.ntnu.idi.idatt2105.project.repository;

import org.ntnu.idi.idatt2105.project.entity.CompletedQuiz;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for the CompletedQuiz entity.
 */
public interface CompletedQuizRepository extends JpaRepository<CompletedQuiz, Long> {}
