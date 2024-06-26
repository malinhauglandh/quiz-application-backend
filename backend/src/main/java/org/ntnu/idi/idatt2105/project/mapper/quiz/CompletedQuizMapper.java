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

    /**
     * Converts a CompletedQuiz entity to a CompletedQuizDTO.
     *
     * @param completedQuiz The CompletedQuiz entity to convert.
     * @return The CompletedQuizDTO.
     */
    public CompletedQuizDTO convertToCompletedQuizDTO(CompletedQuiz completedQuiz) {
        if (completedQuiz == null) {
            return null;
        }

        CompletedQuizDTO dto = new CompletedQuizDTO();
        dto.setCompletedQuizId(completedQuiz.getCompletedQuizId());
        dto.setScore(completedQuiz.getScore());
        dto.setQuizId(completedQuiz.getQuiz().getQuizId());
        dto.setUserId(completedQuiz.getUser().getUserId());
        dto.setCompletedQuizId(completedQuiz.getCompletedQuizId());
        return dto;
    }
}
