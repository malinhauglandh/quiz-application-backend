package org.ntnu.idi.idatt2105.project.repository.quiz;

import java.util.List;
import org.ntnu.idi.idatt2105.project.entity.quiz.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

/** Repository interface for the Quiz entity. */
public interface QuizRepository extends JpaRepository<Quiz, Long> {
    List<Quiz> findByCreator_UserId(Long userId);
}
