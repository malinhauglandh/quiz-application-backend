package org.ntnu.idi.idatt2105.project.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
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

    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Question> questionList;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @JoinTable(
            name = "quiz_users",
            joinColumns = {@JoinColumn(name = "quiz_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")}
    )
    private List<User> users;

    public Quiz() {
    }
}
