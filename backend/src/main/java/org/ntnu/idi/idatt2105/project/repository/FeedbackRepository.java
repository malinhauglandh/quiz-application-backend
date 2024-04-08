package org.ntnu.idi.idatt2105.project.repository;

import org.ntnu.idi.idatt2105.project.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

/** Repository interface for the Feedback entity. */
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {}
