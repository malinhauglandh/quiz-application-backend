package org.ntnu.idi.idatt2105.project.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "question")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int questionId;

    @Column(name = "question_text")
    private String questionText;

    @Column(name = "tag")
    private String tag;

    @Column(name = "multimedia")
    private String multimedia;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_id")
    private QuestionType questionType;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<QuestionChoice> questionChoiceList;

    public Question(String questionText, String tag, String multimedia, Quiz quiz, QuestionType questionType, List<QuestionChoice> questionChoiceList) {
        this.questionText = questionText;
        this.tag = tag;
        this.multimedia = multimedia;
        this.quiz = quiz;
        this.questionType = questionType;
        this.questionChoiceList = questionChoiceList;
    }
}
