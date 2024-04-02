package org.ntnu.idi.idatt2105.project.service;

import jakarta.persistence.EntityNotFoundException;
import org.ntnu.idi.idatt2105.project.entity.Quiz;
import org.ntnu.idi.idatt2105.project.repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuizService {

  private final QuizRepository quizRepository;

  @Autowired
  public QuizService(QuizRepository quizRepository) {
    this.quizRepository = quizRepository;
  }

  public Quiz createQuiz(Quiz quiz) {
    return quizRepository.save(quiz);
  }

  public List<Quiz> getAllQuizzes() {
    return quizRepository.findAll();
  }

  public void deleteQuiz(Long id) {
    Quiz quiz = quizRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Quiz not found with id " + id));
    quizRepository.delete(quiz);
  }
}