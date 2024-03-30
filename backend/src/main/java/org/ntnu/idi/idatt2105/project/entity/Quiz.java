package org.ntnu.idi.idatt2105.project.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "quiz")
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int quizId;

    @Column(name = "quiz_name", nullable = false)
    private String quizName;

    @Column(name = "quiz_description")
    private String quizDescription;

    @Column(name = "multimedia")
    private String multimedia;

    @Column(name = "difficulty_level")
    private String difficultyLevel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id")
    private User creator;

    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Question> questionList;

    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CompletedQuiz> completedQuizList;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "collaborating_users",
            joinColumns = {@JoinColumn(name = "quiz_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")}
    )
    private Set<User> collaboratingUsers;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "favoriteQuizzes")
    private Set<User> favorites;
}
