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
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int category_id;

    @Column(name = "category_name", nullable = false)
    private String categoryName;

    @OneToMany(mappedBy = "category", orphanRemoval = true)
    private List<Quiz> quizList;
}
