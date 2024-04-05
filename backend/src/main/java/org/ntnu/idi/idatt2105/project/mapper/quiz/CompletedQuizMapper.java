package org.ntnu.idi.idatt2105.project.mapper.quiz;

import org.ntnu.idi.idatt2105.project.dto.quiz.CompletedQuizDTO;
import org.ntnu.idi.idatt2105.project.entity.quiz.CompletedQuiz;
import org.springframework.stereotype.Component;

/**
 * Mapper class for the CompletedQuiz entity. Converts CompletedQuiz entities to CompletedQuizDTOs.
 * It is used to convert CompletedQuiz entities to CompletedQuizDTOs before sending the data to the
 * frontend.
 */
@Component
public class CompletedQuizMapper {
    public CompletedQuizDTO convertToCompletedQuizDTO(CompletedQuiz completedQuiz) {
        if (completedQuiz == null) {
            return null;
        }

        CompletedQuizDTO dto = new CompletedQuizDTO();
        dto.setCompletedQuizId((long) completedQuiz.getCompletedQuizId());
        dto.setScore(completedQuiz.getScore());
        dto.setQuizId((long) completedQuiz.getQuiz().getQuizId());
        dto.setUserId((long) completedQuiz.getUser().getUserId());
        dto.setCompletedQuizId((long) completedQuiz.getCompletedQuizId());
        return dto;
    }
}
