package org.ntnu.idi.idatt2105.project.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long category_id;

    @Column(name = "category_name", nullable = false)
    private String categoryName;

    @OneToMany(mappedBy = "category", orphanRemoval = true)
    @JsonIgnore
    private List<Quiz> quizList;

    public long getCategoryId() {
        return category_id;
    }

    public String getCategoryName() {
        return categoryName;
    }
}
