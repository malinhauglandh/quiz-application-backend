package org.ntnu.idi.idatt2105.project.repository;

import java.util.List;
import org.ntnu.idi.idatt2105.project.entity.CompletedQuiz;
import org.ntnu.idi.idatt2105.project.entity.Quiz;
import org.ntnu.idi.idatt2105.project.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/** Repository interface for the CompletedQuiz entity. */
public interface CompletedQuizRepository extends JpaRepository<CompletedQuiz, Long> {
    List<CompletedQuiz> findByUserAndQuiz(User quizId, Quiz userId);
}
