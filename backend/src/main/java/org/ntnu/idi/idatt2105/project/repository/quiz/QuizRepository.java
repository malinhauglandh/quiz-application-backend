package org.ntnu.idi.idatt2105.project.repository.quiz;

import java.util.List;
import org.ntnu.idi.idatt2105.project.entity.quiz.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/** Repository interface for the Quiz entity. */
public interface QuizRepository extends JpaRepository<Quiz, Long> {
    List<Quiz> findByCreator_UserId(Long userId);

    List<Quiz> findByCreator_UserIdAndQuizId(Long creatorId, Long quizId);

    @Query("SELECT q FROM Quiz q LEFT JOIN FETCH q.questionList WHERE q.quizId = :quizId")
    Quiz findByIdWithQuestions(@Param("quizId") Long quizId);
}
