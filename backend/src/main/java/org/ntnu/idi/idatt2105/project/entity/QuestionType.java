package org.ntnu.idi.idatt2105.project.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "question_type")
public class QuestionType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int typeId;

    @Column(name = "type_name")
    private String type;

    @OneToMany(mappedBy = "questionType", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Question> questionList;
}
