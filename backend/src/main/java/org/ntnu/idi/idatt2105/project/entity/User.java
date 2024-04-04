package org.ntnu.idi.idatt2105.project.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** Class representing a user in the application. */
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
@Setter
@NoArgsConstructor
@Table(
        name = "user",
        uniqueConstraints = {
            @UniqueConstraint(name = "UK_user_email", columnNames = "email"),
            @UniqueConstraint(name = "UK_user_username", columnNames = "username")
        })
@Schema(description = "A user of the application.")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @Schema(description = "The user")
    private int userId;

    @Column(name = "username", nullable = false, unique = true)
    @Schema(description = "The username of the user.")
    private String username;

    @Column(name = "email", nullable = false, unique = true)
    @Schema(description = "The email of the user.")
    private String email;

    @Column(name = "password", nullable = false)
    @Schema(description = "The password of the user.")
    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @Schema(description = "The list of feedback from the user.")
    private Set<CompletedQuiz> completedQuizzes = new HashSet<>();

    @OneToMany(mappedBy = "creator", cascade = CascadeType.ALL, orphanRemoval = true)
    @Schema(description = "The list of quizzes created by the user.")
    private Set<Quiz> createdQuizzes = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "favorites",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "quiz_id")})
    @Schema(description = "The list of favorite quizzes by the user.")
    private Set<Quiz> favoriteQuizzes = new HashSet<>();

    @ManyToMany(mappedBy = "collaboratingUsers")
    @Schema(description = "The list of quizzes the user is collaborating on.")
    private Set<Quiz> collaboratingQuizzes = new HashSet<>();
}
