package org.ntnu.idi.idatt2105.project.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "completed_quizzes")
public class CompletedQuiz {
    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int completedQuizId;

    @Getter
    @Setter
    @Column(name = "score")
    private int score;

    @Getter
    @Setter
    @ManyToMany
    @JoinColumn(name = "user_id")
    private List<User> user;

    @Getter
    @Setter
    @ManyToMany
    @JoinColumn(name = "quiz_id")
    private List<Quiz> quiz;

    public CompletedQuiz() {
    }
}
