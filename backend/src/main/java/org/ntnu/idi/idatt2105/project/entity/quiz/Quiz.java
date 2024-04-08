package org.ntnu.idi.idatt2105.project.entity.quiz;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import java.util.List;
import java.util.Set;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.ntnu.idi.idatt2105.project.entity.Category;
import org.ntnu.idi.idatt2105.project.entity.question.Question;
import org.ntnu.idi.idatt2105.project.entity.user.User;

/** Class representing a quiz in the application. */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "quiz")
@Schema(description = "A quiz in the application.")
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "The quiz id, autogenerated by the database.")
    private Long quizId;

    @Column(name = "quiz_name", nullable = false)
    @Schema(description = "The name of the quiz.")
    private String quizName;

    @Column(name = "quiz_description")
    @Schema(description = "The description of the quiz.")
    private String quizDescription;

    @Column(name = "multimedia")
    @Schema(description = "The multimedia of the quiz.")
    private String multimedia;

    @Column(name = "difficulty_level", nullable = false)
    @Schema(description = "The difficulty level of the quiz.")
    private String difficultyLevel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    @Schema(description = "The category of the quiz.")
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id", nullable = false)
    @Schema(description = "The creator of the quiz.")
    private User creator;

    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL, orphanRemoval = true)
    @Schema(description = "The list of questions in the quiz.")
    private Set<Question> questionList;

    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL, orphanRemoval = true)
    @Schema(description = "The list of completed quizzes.")
    private List<CompletedQuiz> completedQuizList;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "collaborating_users",
            joinColumns = {@JoinColumn(name = "quiz_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")})
    @Schema(description = "The list of users collaborating on the quiz.")
    private Set<User> collaboratingUsers;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "favoriteQuizzes")
    @Schema(description = "The list of users that have favorited the quiz.")
    private Set<User> favorites;
}
