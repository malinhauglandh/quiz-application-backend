package org.ntnu.idi.idatt2105.project.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.ntnu.idi.idatt2105.project.dto.CompletedQuizDTO;
import org.ntnu.idi.idatt2105.project.dto.UserAnswerDTO;
import org.ntnu.idi.idatt2105.project.entity.*;
import org.ntnu.idi.idatt2105.project.mapper.CompletedQuizMapper;
import org.ntnu.idi.idatt2105.project.mapper.UserAnswerMapper;
import org.ntnu.idi.idatt2105.project.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class for the CompletedQuiz entity.
 * This class contains methods for submitting user answers and getting completed quizzes for a user.
 */
@Service
public class CompletedQuizService {
  private final CompletedQuizRepository completedQuizRepository;
  private final CompletedQuizMapper completedQuizMapper;
  private final QuizRepository quizRepository;
  private final UserRepository userRepository;
  private final UserAnswerRepository userAnswerRepository;
  private final UserAnswerMapper userAnswerMapper;
  private final QuestionChoiceRepository questionChoiceRepository;

  @Autowired
  public CompletedQuizService(CompletedQuizRepository completedQuizRepository,
                              CompletedQuizMapper completedQuizMapper,
                              QuizRepository quizRepository,
                              UserRepository userRepository,
                              UserAnswerRepository userAnswerRepository,
                              UserAnswerMapper userAnswerMapper,
                              QuestionChoiceRepository questionChoiceRepository) {
    this.completedQuizRepository = completedQuizRepository;
    this.completedQuizMapper = completedQuizMapper;
    this.quizRepository = quizRepository;
    this.userRepository = userRepository;
    this.userAnswerRepository = userAnswerRepository;
    this.userAnswerMapper = userAnswerMapper;
    this.questionChoiceRepository = questionChoiceRepository;
  }

  /**
   * Submits user answers to a quiz, and calculates the score.
   *
   * @param userId The id of the user submitting the answers
   * @param quizId The id of the quiz the user is submitting answers to
   * @param answerDTOs A list of UserAnswerDTOs containing the user's answers
   * @return A CompletedQuizDTO containing the user's answers and the score
   */
  @Transactional
  public CompletedQuizDTO submitUserAnswers(Long userId, Long quizId, List<UserAnswerDTO> answerDTOs) {
    User user = userRepository.findById(userId)
            .orElseThrow(() -> new EntityNotFoundException("User not found"));

    Quiz quiz = quizRepository.findById(quizId)
            .orElseThrow(() -> new EntityNotFoundException("Quiz not found"));

    int score = 0;

    CompletedQuiz completedQuiz = new CompletedQuiz();
    completedQuiz.setUser(user);
    completedQuiz.setQuiz(quiz);
    completedQuiz.setScore(score);

    completedQuiz = completedQuizRepository.save(completedQuiz);

    for (UserAnswerDTO answerDTO : answerDTOs) {
      QuestionChoice questionChoice = questionChoiceRepository.findById(answerDTO.getQuestionChoiceId())
              .orElseThrow(() -> new EntityNotFoundException("QuestionChoice not found"));

      UserAnswer userAnswer = new UserAnswer();
      userAnswer.setCompletedQuiz(completedQuiz);
      userAnswer.setQuestionChoice(questionChoice);
      userAnswerRepository.save(userAnswer);

      if (questionChoice.isCorrectChoice()) {
        score++;
      }
    }

    completedQuiz.setScore(score);
    completedQuizRepository.save(completedQuiz);

    CompletedQuizDTO completedQuizDTO = completedQuizMapper.convertToCompletedQuizDTO(completedQuiz);
    completedQuizDTO.setUserAnswers(answerDTOs);

    return completedQuizDTO;
  }

  /**
   * Gets all completed quizzes for a user.
   *
   * @param userId The id of the user
   * @param quizId The id of the quiz
   * @return A list of CompletedQuizDTOs containing the user's answers and the score
   */
  @Transactional
  public List<CompletedQuizDTO> getCompletedQuizzesForUser(Long userId, Long quizId) {
    User user = userRepository.findById(userId)
            .orElseThrow(() -> new EntityNotFoundException("User not found"));

    Quiz quiz = quizRepository.findById(quizId)
            .orElseThrow(() -> new EntityNotFoundException("Quiz not found"));

    List<CompletedQuiz> completedQuizzes = completedQuizRepository.findByUserAndQuiz(user, quiz);

    return completedQuizzes.stream()
            .map(completedQuiz -> {
              CompletedQuizDTO dto = completedQuizMapper.convertToCompletedQuizDTO(completedQuiz);
              List<UserAnswerDTO> userAnswerDTOs = userAnswerRepository.findByCompletedQuizCompletedQuizId((long)completedQuiz.getCompletedQuizId())
                      .stream()
                      .map(userAnswerMapper::convertUserAnswerToDTO)
                      .collect(Collectors.toList());
              dto.setUserAnswers(userAnswerDTOs);
              return dto;
            })
            .collect(Collectors.toList());
  }
}
