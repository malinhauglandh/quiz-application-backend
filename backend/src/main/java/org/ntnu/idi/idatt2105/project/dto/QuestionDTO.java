package org.ntnu.idi.idatt2105.project.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Data transfer object representing a question in the application.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QuestionDTO {
    /**
     * The question id, autogenerated by the database.
     */
  private Long questionId;
    /**
     * The question text.
     */
  private String questionText;
    /**
     * The tag of the question, to easier categorize similar questions.
     */
  private String tag;
    /**
     * The multimedia of the question.
     */
  private String multimedia;
    /**
     * The quiz the question belongs to.
     */
  private Long quizId;
    /**
     * The type of question.
     */
  private Long questionTypeId;
    /**
     * The list of choices for the question.
     */
  private List<QuestionChoiceDTO> choices = new ArrayList<>();
}


