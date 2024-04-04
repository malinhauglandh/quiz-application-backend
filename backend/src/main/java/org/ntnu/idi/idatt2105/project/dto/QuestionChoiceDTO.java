package org.ntnu.idi.idatt2105.project.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class QuestionChoiceDTO {
  private Long quizChoiceId;
  private String choice;
  private String explanation;
  private boolean isCorrectChoice;
}
