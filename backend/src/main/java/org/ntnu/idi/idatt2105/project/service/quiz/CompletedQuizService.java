package org.ntnu.idi.idatt2105.project.service.quiz;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.ntnu.idi.idatt2105.project.dto.quiz.CompletedQuizDTO;
import org.ntnu.idi.idatt2105.project.dto.user.UserAnswerDTO;
import org.ntnu.idi.idatt2105.project.entity.question.QuestionChoice;
import org.ntnu.idi.idatt2105.project.entity.quiz.CompletedQuiz;
import org.ntnu.idi.idatt2105.project.entity.quiz.Quiz;
import org.ntnu.idi.idatt2105.project.entity.user.User;
import org.ntnu.idi.idatt2105.project.entity.user.UserAnswer;
import org.ntnu.idi.idatt2105.project.mapper.quiz.CompletedQuizMapper;
import org.ntnu.idi.idatt2105.project.mapper.user.UserAnswerMapper;
import org.ntnu.idi.idatt2105.project.repository.question.QuestionChoiceRepository;
import org.ntnu.idi.idatt2105.project.repository.quiz.CompletedQuizRepository;
import org.ntnu.idi.idatt2105.project.repository.quiz.QuizRepository;
import org.ntnu.idi.idatt2105.project.repository.user.UserAnswerRepository;
import org.ntnu.idi.idatt2105.project.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service class for the CompletedQuiz entity. This class contains methods for submitting user
 * answers and getting completed quizzes for a user.
 */
@Service
public class CompletedQuizService {

    /** Repositories used by the service. */
    private final CompletedQuizRepository completedQuizRepository;

    /** Mappers used by the service. */
    private final CompletedQuizMapper completedQuizMapper;

    /** Repositories used by the service. */
    private final QuizRepository quizRepository;

    /** Repositories used by the service. */
    private final UserRepository userRepository;

    /** Repositories used by the service. */
    private final UserAnswerRepository userAnswerRepository;

    /** Mappers used by the service. */
    private final UserAnswerMapper userAnswerMapper;

    /** Repositories used by the service. */
    private final QuestionChoiceRepository questionChoiceRepository;

    /**
     * Constructor for CompletedQuizService.
     *
     * @param completedQuizRepository completedQuizRepository
     * @param completedQuizMapper completedQuizMapper
     * @param quizRepository quizRepository
     * @param userRepository userRepository
     * @param userAnswerRepository userAnswerRepository
     * @param userAnswerMapper userAnswerMapper
     * @param questionChoiceRepository questionChoiceRepository
     */
    @Autowired
    public CompletedQuizService(
            CompletedQuizRepository completedQuizRepository,
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
    public CompletedQuizDTO submitUserAnswers(
            Long userId, Long quizId, List<UserAnswerDTO> answerDTOs) {
        User user =
                userRepository
                        .findById(userId)
                        .orElseThrow(() -> new EntityNotFoundException("User not found"));

        Quiz quiz =
                quizRepository
                        .findById(quizId)
                        .orElseThrow(() -> new EntityNotFoundException("Quiz not found"));

        int score = 0;

        CompletedQuiz completedQuiz = new CompletedQuiz();
        completedQuiz.setUser(user);
        completedQuiz.setQuiz(quiz);
        completedQuiz.setScore(score);

        completedQuiz = completedQuizRepository.save(completedQuiz);

        List<UserAnswerDTO> userAnswerDTOs = new ArrayList<>();

        for (UserAnswerDTO answerDTO : answerDTOs) {
            QuestionChoice questionChoice =
                    questionChoiceRepository
                            .findById(answerDTO.getQuestionChoiceId())
                            .orElseThrow(
                                    () -> new EntityNotFoundException("QuestionChoice not found"));

            answerDTO.setCorrect(questionChoice.isCorrectChoice());
            answerDTO.setExplanation(questionChoice.getExplanation());
            answerDTO.setQuestionText(questionChoice.getQuestion().getQuestionText());


            UserAnswer userAnswer = new UserAnswer();
            userAnswer.setCompletedQuiz(completedQuiz);
            userAnswer.setQuestionChoice(questionChoice);
            userAnswerRepository.save(userAnswer);

            if (questionChoice.isCorrectChoice()) {
                score++;
            }

            userAnswerDTOs.add(answerDTO);
        }

        completedQuiz.setScore(score);
        completedQuizRepository.save(completedQuiz);

        CompletedQuizDTO completedQuizDTO =
                completedQuizMapper.convertToCompletedQuizDTO(completedQuiz);
        completedQuizDTO.setUserAnswers(answerDTOs);

        return completedQuizDTO;
    }

    /**
     * Gets the latest completed quiz attempt by a user for a specific quiz.
     *
     * @param userId The id of the user
     * @param quizId The id of the quiz
     * @return A list of CompletedQuizDTOs containing the user's answers and the score
     */
    @Transactional
    public CompletedQuizDTO getLatestCompletedQuizForUser(Long userId, Long quizId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new EntityNotFoundException("Quiz not found"));

        List<CompletedQuiz> completedQuizzes = completedQuizRepository.findByUserAndQuiz(user, quiz);

        if (completedQuizzes.isEmpty()) {
            throw new EntityNotFoundException("Completed quizzes not found");
        }

        CompletedQuiz latestCompletedQuiz = completedQuizzes.get(completedQuizzes.size() - 1);

        CompletedQuizDTO dto = completedQuizMapper.convertToCompletedQuizDTO(latestCompletedQuiz);
        List<UserAnswerDTO> userAnswerDTOs = userAnswerRepository
                .findByCompletedQuizCompletedQuizId(latestCompletedQuiz.getCompletedQuizId())
                .stream()
                .map(userAnswerMapper::convertUserAnswerToDTO)
                .collect(Collectors.toList());
        dto.setUserAnswers(userAnswerDTOs);
        return dto;
    }
}