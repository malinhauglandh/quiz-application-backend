package org.ntnu.idi.idatt2105.project.service.quiz;

import java.util.List;
import java.util.stream.Collectors;

import jakarta.persistence.EntityNotFoundException;
import org.ntnu.idi.idatt2105.project.dto.question.QuestionDTO;
import org.ntnu.idi.idatt2105.project.dto.quiz.CreateQuizDTO;
import org.ntnu.idi.idatt2105.project.dto.quiz.QuizWithQuestionsDTO;
import org.ntnu.idi.idatt2105.project.entity.Category;
import org.ntnu.idi.idatt2105.project.entity.question.Question;
import org.ntnu.idi.idatt2105.project.entity.quiz.Quiz;
import org.ntnu.idi.idatt2105.project.entity.user.User;
import org.ntnu.idi.idatt2105.project.exception.CategoryNotFoundException;
import org.ntnu.idi.idatt2105.project.exception.QuizNotFoundException;
import org.ntnu.idi.idatt2105.project.exception.UserNotFoundException;
import org.ntnu.idi.idatt2105.project.mapper.question.QuestionMapper;
import org.ntnu.idi.idatt2105.project.mapper.quiz.QuizMapper;
import org.ntnu.idi.idatt2105.project.repository.CategoryRepository;
import org.ntnu.idi.idatt2105.project.repository.quiz.QuizRepository;
import org.ntnu.idi.idatt2105.project.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** Service class for Quiz */
@Service
public class QuizService {

    /** Repository for quiz */
    private final QuizRepository quizRepository;

    /** Repository for Category */
    private final CategoryRepository categoryRepository;

    /** Repository for User */
    private final UserRepository userRepository;

    /** Mapper for Quiz */
    private final QuizMapper quizMapper;

    /** Mapper for Question */
    private final QuestionMapper questionMapper;

    /**
     * Constructor for QuizService
     *
     * @param quizRepository quizRepository
     * @param quizMapper quizMapper
     */
    @Autowired
    public QuizService(
            QuizRepository quizRepository,
            CategoryRepository categoryRepository,
            UserRepository userRepository,
            QuizMapper quizMapper,
            QuestionMapper questionMapper) {
        this.quizRepository = quizRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
        this.quizMapper = quizMapper;
        this.questionMapper = questionMapper;
    }

    /**
     * Create a new quiz
     *
     * @param quizDTO The quiz to create
     * @return The created quiz
     */
    public CreateQuizDTO createQuiz(CreateQuizDTO quizDTO) {
        Quiz quiz = quizMapper.toEntity(quizDTO);

        Category category =
                categoryRepository
                        .findById(quizDTO.getCategoryId())
                        .orElseThrow(() -> new CategoryNotFoundException("Category not found"));
        User creator =
                userRepository
                        .findById(quizDTO.getCreatorId())
                        .orElseThrow(() -> new UserNotFoundException("User not found"));

        quiz.setCategory(category);
        quiz.setCreator(creator);

        Quiz newQuiz = quizRepository.save(quiz);

        return quizMapper.toDto(newQuiz);
    }

    /**
     * Get all quizzes
     *
     * @return A list of all quizzes
     */
    public List<CreateQuizDTO> getAllQuizzes() {
        List<Quiz> quizzes = quizRepository.findAll();

        return quizzes.stream().map(quizMapper::toDto).collect(Collectors.toList());
    }

    /**
     * Get a quiz by its id
     *
     * @param quizId The id of the quiz
     * @return The quiz
     */
    public Quiz findQuizById(Long quizId) {
        return quizRepository.findById(quizId).orElse(null);
    }

    /**
     * Get all quizzes created by a user
     *
     * @param creatorId The id of the user
     * @return A list of quizzes created by the user
     */
    public List<Quiz> getQuizzesByCreatorId(Long creatorId) {
        return quizRepository.findByCreator_UserId(creatorId);
    }

    /**
     * Get all quizzes created by a user with a specific id
     *
     * @param creatorId The id of the user
     * @param quizId The id of the quiz
     * @return A list of quizzes created by the user with the specific id
     */
    public List<Quiz> getQuizzesByCreatorIdAndQuizId(Long creatorId, Long quizId) {
        return quizRepository.findByCreator_UserIdAndQuizId(creatorId, quizId);

    }

    /**
     * Get a quiz with its questions
     *
     * @param quizId The id of the quiz
     * @return The quiz with its questions
     */
    @Transactional(readOnly = true)
    public QuizWithQuestionsDTO getQuizWithQuestions(Long quizId) {
        Quiz quiz = quizRepository.findByIdWithQuestions(quizId);
        if (quiz == null) {
            throw new EntityNotFoundException("Quiz with ID " + quizId + " not found");
        }
        return quizMapper.toQuizWithQuestionsDTO(quiz);
    }

    /**
     * Delete a quiz by its id
     * @param quizId for the quiz to delete
     */
    public void deleteQuiz(Long quizId) {
        if(!quizRepository.existsById(quizId)) {
            throw new QuizNotFoundException("Quiz not found");
        } else {
            quizRepository.deleteById(quizId);
        }
    }


    /**

     Get a question by its id from a quiz.**/
    public QuestionDTO getQuestionById(Long quizId, Long questionId) {
        Quiz quiz = quizRepository.findById(quizId).orElseThrow(() ->
                new EntityNotFoundException("Quiz with ID " + quizId + " not found"));

        Question question = quiz.getQuestionList().stream()
                .filter(q -> q.getQuestionId().equals(questionId))
                .findFirst()
                .orElseThrow(() ->
                        new EntityNotFoundException("Question with ID " + questionId + " not found"));

        return questionMapper.questionToQuestionDTO(question);
    }
}
