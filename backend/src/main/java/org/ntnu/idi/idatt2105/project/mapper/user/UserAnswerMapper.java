package org.ntnu.idi.idatt2105.project.mapper.user;

import org.ntnu.idi.idatt2105.project.dto.user.UserAnswerDTO;
import org.ntnu.idi.idatt2105.project.entity.user.UserAnswer;
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
        dto.setUserAnswerId(userAnswer.getAnswerId());
        dto.setCompletedQuizId(
                userAnswer.getCompletedQuiz() != null
                        ? userAnswer.getCompletedQuiz().getCompletedQuizId()
                        : null);
        dto.setQuestionChoiceId(
                userAnswer.getQuestionChoice() != null
                        ? userAnswer.getQuestionChoice().getQuizChoiceId()
                        : null);

        return dto;
    }
}
