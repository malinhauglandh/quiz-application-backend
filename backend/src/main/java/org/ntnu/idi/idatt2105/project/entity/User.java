package org.ntnu.idi.idatt2105.project.entity;

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
@Table(name = "user", uniqueConstraints = {
        @UniqueConstraint(name = "UK_user_email", columnNames = "email"),
        @UniqueConstraint(name = "UK_user_username", columnNames = "username")
})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int userId;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CompletedQuiz> completedQuizzes = new HashSet<>();

    @OneToMany(mappedBy = "creator", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Quiz> createdQuizzes = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "favorites",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "quiz_id")})
    private Set<Quiz> favoriteQuizzes = new HashSet<>();

    @ManyToMany(mappedBy = "collaboratingUsers")
    private Set<Quiz> collaboratingQuizzes = new HashSet<>();
}
