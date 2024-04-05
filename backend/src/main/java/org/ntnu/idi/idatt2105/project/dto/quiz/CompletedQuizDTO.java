package org.ntnu.idi.idatt2105.project.dto.quiz;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ntnu.idi.idatt2105.project.dto.user.UserAnswerDTO;

/** Data transfer object for CompletedQuiz entity. */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompletedQuizDTO {
    private Long completedQuizId;
    private int score;
    private Long userId;
    private Long quizId;
    private List<UserAnswerDTO> userAnswers;
}