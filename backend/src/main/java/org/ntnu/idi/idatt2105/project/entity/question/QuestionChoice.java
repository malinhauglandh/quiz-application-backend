package org.ntnu.idi.idatt2105.project.entity.question;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.ntnu.idi.idatt2105.project.entity.user.UserAnswer;

/** Class representing a question choice in the application. */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "question_choices")
@Schema(description = "A choice for a question in a quiz.")
public class QuestionChoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "The question choice id, autogenerated by the database.")
    private Long quizChoiceId;

    @Column(name = "choice")
    @Schema(description = "The choice text.")
    private String choice;

    @Column(name = "explanation")
    @Schema(description = "An explanation of the choice on why it is right or wrong.")
    private String explanation;

    @Setter
    @Column(name = "is_correct_choice")
    @Schema(description = "If the choice is the correct choice.")
    private boolean isCorrectChoice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    @Schema(description = "The question the choice belongs to.")
    private Question question;

    @OneToMany(mappedBy = "questionChoice", cascade = CascadeType.ALL, orphanRemoval = true)
    @Schema(description = "The list of user answers for the choice.")
    private List<UserAnswer> userAnswerList;

    public QuestionChoice(
            Long quizChoiceId,
            String choice,
            String explanation,
            boolean correctChoice,
            Question question) {
        this.quizChoiceId = quizChoiceId;
        this.choice = choice;
        this.explanation = explanation;
        this.isCorrectChoice = correctChoice;
        this.question = question;
    }
}
