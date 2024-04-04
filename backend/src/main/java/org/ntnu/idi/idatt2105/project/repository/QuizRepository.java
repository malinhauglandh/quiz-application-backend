package org.ntnu.idi.idatt2105.project.repository;

import org.ntnu.idi.idatt2105.project.entity.Quiz;
import org.ntnu.idi.idatt2105.project.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/** Repository interface for the Quiz entity. */
public interface QuizRepository extends JpaRepository<Quiz, Long> {

    // find quiz by id of creator
    List<Quiz> getAllByCreator(User creator);
}
