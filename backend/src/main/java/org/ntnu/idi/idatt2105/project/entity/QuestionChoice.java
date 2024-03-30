package org.ntnu.idi.idatt2105.project.entity;

import jakarta.persistence.*;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "question_choices")
public class QuestionChoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int quizChoiceId;

    @Column(name = "choice")
    private String choice;

    @Column(name = "explanation")
    private String explanation;

    @Column(name = "is_correct_choice")
    private boolean isCorrectChoice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    private Question question;

    @OneToMany(mappedBy = "questionChoice", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserAnswer> userAnswerList;
}