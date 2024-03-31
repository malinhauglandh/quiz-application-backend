package org.ntnu.idi.idatt2105.project.repository;

import org.ntnu.idi.idatt2105.project.entity.QuestionType;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for the QuestionType entity.
 */
public interface QuestionTypeRepository extends JpaRepository<QuestionType, Long> {}
