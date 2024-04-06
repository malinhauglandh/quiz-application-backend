package org.ntnu.idi.idatt2105.project.mapper.user;

import org.ntnu.idi.idatt2105.project.dto.user.UserAnswerDTO;
import org.ntnu.idi.idatt2105.project.entity.user.UserAnswer;
import org.springframework.stereotype.Component;

/**

 Mapper class for the UserAnswer entity. Converts UserAnswer entities to UserAnswerDTOs. It is
 used to convert UserAnswer entities to UserAnswerDTOs before sending the data to the frontend.
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

        if (userAnswer.getQuestionChoice() != null) {
            dto.setChoice(userAnswer.getQuestionChoice().getChoice());
            dto.setCorrect(userAnswer.getQuestionChoice().isCorrectChoice());
            dto.setExplanation(userAnswer.getQuestionChoice().getExplanation());
            dto.setQuestionText(userAnswer.getQuestionChoice().getQuestion().getQuestionText());
        }

        return dto;
    }
}