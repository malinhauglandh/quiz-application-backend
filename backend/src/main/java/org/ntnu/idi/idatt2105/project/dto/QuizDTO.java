package org.ntnu.idi.idatt2105.project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuizDTO {
    private Long quizId;
    private String quizName;
    private String quizDescription;
    private String multimedia;
    private String difficultyLevel;
    private Long categoryId;
    private Long creatorId;
}
