package org.ntnu.idi.idatt2105.project.service;

import java.util.List;
import java.util.stream.Collectors;

import org.ntnu.idi.idatt2105.project.dto.QuizDTO;
import org.ntnu.idi.idatt2105.project.entity.Category;
import org.ntnu.idi.idatt2105.project.entity.Quiz;
import org.ntnu.idi.idatt2105.project.entity.User;
import org.ntnu.idi.idatt2105.project.exception.CategoryNotFoundException;
import org.ntnu.idi.idatt2105.project.exception.UserNotFoundException;
import org.ntnu.idi.idatt2105.project.repository.CategoryRepository;
import org.ntnu.idi.idatt2105.project.repository.QuizRepository;
import org.ntnu.idi.idatt2105.project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service class for Quiz

 */
@Service
public class QuizService {

    /**
     * Repository for Quiz
     */
    private final QuizRepository quizRepository;

    /**
     * Repository for Category
     */
    private final CategoryRepository categoryRepository;

    /**
     * Repository for User
     */
    private final UserRepository userRepository;

    /**
     * Mapper for Quiz
     */
    private final QuizMapper quizMapper;

    /**
     * Constructor for QuizService
     * @param quizRepository quizRepository
     * @param categoryRepository categoryRepository
     * @param userRepository userRepository
     * @param quizMapper quizMapper
     */
    @Autowired
    public QuizService(QuizRepository quizRepository,
                       CategoryRepository categoryRepository,
                       UserRepository userRepository,
                       QuizMapper quizMapper) {
        this.quizRepository = quizRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
        this.quizMapper = quizMapper;
    }

    /**
     * Create a new quiz
     * @param quizDTO The quiz to create
     * @return The created quiz
     */
    public QuizDTO createQuiz(QuizDTO quizDTO) {
        Quiz quiz = quizMapper.toEntity(quizDTO);

        Category category = categoryRepository.findById(quizDTO.getCategoryId()).orElseThrow(
                () -> new CategoryNotFoundException("Category not found"));
        User creator = userRepository.findById(quizDTO.getCreatorId()).orElseThrow(
                () -> new UserNotFoundException("User not found"));

        quiz.setCategory(category);
        quiz.setCreator(creator);

        Quiz newQuiz = quizRepository.save(quiz);

        return quizMapper.toDto(newQuiz);
    }

    /**
     * Get all quizzes
     * @return A list of all quizzes
     */
    public List<QuizDTO> getAllQuizzes() {
        List<Quiz> quizzes = quizRepository.findAll();

        return quizzes.stream()
                .map(quizMapper::toDto)
                .collect(Collectors.toList());
    }


    /**
     * Get a quiz by its id
     * @param quizId The id of the quiz
     * @return The quiz
     */
      public Quiz findQuizById(Long quizId) {
        return quizRepository.findById(quizId).orElse(null);
      }

    /**
     * Get all quizzes created by a user
     * @param creatorId The id of the user
     * @return A list of quizzes created by the user
     */
    public List<Quiz> getQuizzesByCreatorId(Long creatorId) {
        return quizRepository.findByCreator_UserId(creatorId);
    }
}
