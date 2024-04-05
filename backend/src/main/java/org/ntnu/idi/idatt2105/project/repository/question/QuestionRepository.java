package org.ntnu.idi.idatt2105.project.repository.question;

import org.ntnu.idi.idatt2105.project.entity.question.Question;
import org.springframework.data.jpa.repository.JpaRepository;

/** Repository interface for the Question entity. */
public interface QuestionRepository extends JpaRepository<Question, Long> {}
