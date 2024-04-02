package org.ntnu.idi.idatt2105.project.service;

import org.ntnu.idi.idatt2105.project.entity.QuestionChoice;
import org.ntnu.idi.idatt2105.project.repository.QuestionChoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionChoiceService {

  private final QuestionChoiceRepository questionChoiceRepository;

  @Autowired
  public QuestionChoiceService(QuestionChoiceRepository questionChoiceRepository) {
    this.questionChoiceRepository = questionChoiceRepository;
  }

  public QuestionChoice addQuestionChoice(QuestionChoice questionChoice) {
    return questionChoiceRepository.save(questionChoice);
  }

  public List<QuestionChoice> getChoicesByQuestionId(Long questionId) {
    return questionChoiceRepository.findByQuestionId(questionId);
  }

  // Additional methods for updating and deleting question choices
}