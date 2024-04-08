package org.ntnu.idi.idatt2105.project.dto.quiz;

import lombok.*;
import org.ntnu.idi.idatt2105.project.dto.question.QuestionDTO;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class QuizWithQuestionsDTO extends CreateQuizDTO{
    private List<QuestionDTO> questions;
}
