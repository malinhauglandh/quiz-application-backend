package org.ntnu.idi.idatt2105.project.service;

import org.ntnu.idi.idatt2105.project.entity.Question;
import org.ntnu.idi.idatt2105.project.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {

  private final QuestionRepository questionRepository;

  @Autowired
  public QuestionService(QuestionRepository questionRepository) {
    this.questionRepository = questionRepository;
  }

  public Question addQuestion(Question question) {
    return questionRepository.save(question);
  }

  public Question updateQuestion(Question question) {
    // Make sure to handle cases where the question might not exist
    return questionRepository.save(question);
  }

  public void deleteQuestion(Long id) {
    questionRepository.deleteById(id);
  }

  public List<Question> findAllQuestions() {
    return questionRepository.findAll();
  }
}