package org.ntnu.idi.idatt2105.project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Data transfer object for UserAnswer entity. */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAnswerDTO {
    private Long userAnswerId;
    private Long completedQuizId;
    private Long questionChoiceId;
}
