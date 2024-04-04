package org.ntnu.idi.idatt2105.project.repository;

import org.ntnu.idi.idatt2105.project.entity.Question;
import org.ntnu.idi.idatt2105.project.entity.QuestionChoice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/** Repository interface for the QuestionChoice entity. */
public interface QuestionChoiceRepository extends JpaRepository<QuestionChoice, Long> {
  List<QuestionChoice> findAllByQuestion(Question question);
}
