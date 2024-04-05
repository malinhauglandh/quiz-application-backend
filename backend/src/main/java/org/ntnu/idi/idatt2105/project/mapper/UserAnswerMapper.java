package org.ntnu.idi.idatt2105.project.mapper;

import org.ntnu.idi.idatt2105.project.dto.UserAnswerDTO;
import org.ntnu.idi.idatt2105.project.entity.UserAnswer;
import org.springframework.stereotype.Component;

/**
 * Mapper class for the UserAnswer entity. Converts UserAnswer entities to UserAnswerDTOs. It is
 * used to convert UserAnswer entities to UserAnswerDTOs before sending the data to the frontend.
 */
@Component
public class UserAnswerMapper {
    public UserAnswerDTO convertUserAnswerToDTO(UserAnswer userAnswer) {
        if (userAnswer == null) {
            return null;
        }

        UserAnswerDTO dto = new UserAnswerDTO();
        dto.setUserAnswerId((long) userAnswer.getAnswerId());
        dto.setCompletedQuizId(
                userAnswer.getCompletedQuiz() != null
                        ? (long) userAnswer.getCompletedQuiz().getCompletedQuizId()
                        : null);
        dto.setQuestionChoiceId(
                userAnswer.getQuestionChoice() != null
                        ? (long) userAnswer.getQuestionChoice().getQuizChoiceId()
                        : null);

        return dto;
    }
}
