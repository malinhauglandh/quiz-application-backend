package org.ntnu.idi.idatt2105.project.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuestionChoiceDTO {
  private long quizChoiceId;
  private String choice;
  private String explanation;
  private boolean isCorrectChoice;

  public void setIsCorrectChoice(boolean isCorrectChoice) {
    this.isCorrectChoice = isCorrectChoice;
  }
}
