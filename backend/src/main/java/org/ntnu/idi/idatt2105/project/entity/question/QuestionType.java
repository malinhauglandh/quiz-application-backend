package org.ntnu.idi.idatt2105.project.entity.question;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** Class representing a question type in the application. */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "question_type")
@Schema(description = "A type of question.")
public class QuestionType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "The question type id, autogenerated by the database.")
    private Long typeId;

    @Column(name = "type_name")
    @Schema(description = "The name of the question type.")
    private String type;

    @OneToMany(mappedBy = "questionType", cascade = CascadeType.ALL, orphanRemoval = true)
    @Schema(description = "The list of questions of the type.")
    private List<Question> questionList;
}
