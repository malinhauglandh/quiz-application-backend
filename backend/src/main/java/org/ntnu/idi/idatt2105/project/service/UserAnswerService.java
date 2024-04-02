package org.ntnu.idi.idatt2105.project.service;

import org.ntnu.idi.idatt2105.project.entity.UserAnswer;
import org.ntnu.idi.idatt2105.project.repository.UserAnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserAnswerService {

  private final UserAnswerRepository userAnswerRepository;

  @Autowired
  public UserAnswerService(UserAnswerRepository userAnswerRepository) {
    this.userAnswerRepository = userAnswerRepository;
  }

  public UserAnswer saveUserAnswer(UserAnswer userAnswer) {
    return userAnswerRepository.save(userAnswer);
  }

  public List<UserAnswer> getUserAnswersByUserId(Long userId) {
    return userAnswerRepository.findByUserId(userId);
  }
}