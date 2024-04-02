package org.ntnu.idi.idatt2105.project.repository;

import org.ntnu.idi.idatt2105.project.entity.UserAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

/** Repository interface for the UserAnswer entity. */
public interface UserAnswerRepository extends JpaRepository<UserAnswer, Long> {}
