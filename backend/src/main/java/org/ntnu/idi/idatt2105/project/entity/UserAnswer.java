package org.ntnu.idi.idatt2105.project.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "user_answers")
@NoArgsConstructor
public class UserAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int answerId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "completed_quiz_id")
    private CompletedQuiz completedQuiz;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_choice_id")
    private QuestionChoice questionChoice;
}