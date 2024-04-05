package org.ntnu.idi.idatt2105.project.repository.question;

import java.util.List;
import org.ntnu.idi.idatt2105.project.entity.question.Question;
import org.ntnu.idi.idatt2105.project.entity.question.QuestionChoice;
import org.springframework.data.jpa.repository.JpaRepository;

/** Repository interface for the QuestionChoice entity. */
public interface QuestionChoiceRepository extends JpaRepository<QuestionChoice, Long> {
    List<QuestionChoice> findAllByQuestion(Question question);
}
