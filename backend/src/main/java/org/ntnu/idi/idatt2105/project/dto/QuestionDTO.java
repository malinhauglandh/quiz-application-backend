package org.ntnu.idi.idatt2105.project.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QuestionDTO {
  private Long questionId;
  private String questionText;
  private String tag;
  private String multimedia;
  private Long quizId;
  private Long questionTypeId;
  private List<QuestionChoiceDTO> choices = new ArrayList<>();
}


