package org.ntnu.idi.idatt2105.project.entity;

import jakarta.persistence.*;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** Class representing a category in the application. */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "completed_quizzes")
public class CompletedQuiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long completedQuizId;

    @Column(name = "score")
    private int score;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;

    @OneToMany(mappedBy = "completedQuiz", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserAnswer> userAnswerList;
}
