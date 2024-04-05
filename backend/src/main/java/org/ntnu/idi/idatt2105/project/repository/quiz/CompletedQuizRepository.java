package org.ntnu.idi.idatt2105.project.repository.quiz;

import java.util.List;
import org.ntnu.idi.idatt2105.project.entity.quiz.CompletedQuiz;
import org.ntnu.idi.idatt2105.project.entity.quiz.Quiz;
import org.ntnu.idi.idatt2105.project.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

/** Repository interface for the CompletedQuiz entity. */
public interface CompletedQuizRepository extends JpaRepository<CompletedQuiz, Long> {
    List<CompletedQuiz> findByUserAndQuiz(User quizId, Quiz userId);
}
