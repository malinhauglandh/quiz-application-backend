package org.ntnu.idi.idatt2105.project.service;

import org.ntnu.idi.idatt2105.project.entity.CompletedQuiz;
import org.ntnu.idi.idatt2105.project.entity.Question;
import org.ntnu.idi.idatt2105.project.repository.CompletedQuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompletedQuizService {

  private final CompletedQuizRepository completedQuizRepository;

  @Autowired
  public CompletedQuizService(CompletedQuizRepository completedQuizRepository) {
    this.completedQuizRepository = completedQuizRepository;
  }

  public CompletedQuiz recordQuizCompletion(CompletedQuiz completedQuiz) {
    return completedQuizRepository.save(completedQuiz);
  }

  public List<CompletedQuiz> findAllCompletedQuizzes() {
    return completedQuizRepository.findAll();
  }
}