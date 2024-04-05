package org.ntnu.idi.idatt2105.project.service;

import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.ntnu.idi.idatt2105.project.dto.quiz.QuizDTO;
import org.ntnu.idi.idatt2105.project.entity.Category;
import org.ntnu.idi.idatt2105.project.entity.quiz.Quiz;
import org.ntnu.idi.idatt2105.project.entity.user.User;
import org.ntnu.idi.idatt2105.project.mapper.quiz.QuizMapper;
import org.ntnu.idi.idatt2105.project.repository.CategoryRepository;
import org.ntnu.idi.idatt2105.project.repository.quiz.QuizRepository;
import org.ntnu.idi.idatt2105.project.repository.user.UserRepository;
import org.ntnu.idi.idatt2105.project.service.quiz.QuizService;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.*;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class QuizServiceTest {

    @Mock
    private QuizRepository quizRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private QuizMapper quizMapper;

    @InjectMocks
    private QuizService quizService;

    private Quiz quiz;
    private QuizDTO quizDTO;
    private User user;
    private Category category;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setUserId(1L);
        user.setUsername("testUser");

        category = new Category();
        category.setCategory_id(1L);
        category.setCategoryName("Test Category");

        quiz = new Quiz();
        quiz.setQuizId(1L);
        quiz.setQuizName("Test Quiz");
        quiz.setQuizDescription("Test Description");
        quiz.setDifficultyLevel("Easy");
        quiz.setCreator(user);
        quiz.setCategory(category);

        quizDTO = new QuizDTO();
        quizDTO.setQuizId(quiz.getQuizId());
        quizDTO.setQuizName(quiz.getQuizName());
        quizDTO.setQuizDescription(quiz.getQuizDescription());
        quizDTO.setDifficultyLevel(quiz.getDifficultyLevel());
        quizDTO.setCategoryId(category.getCategory_id());
        quizDTO.setCreatorId(user.getUserId());
    }

    @Test
    void verifyCreateQuizCreatesQuiz() {
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(category));
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(quizMapper.toEntity(any(QuizDTO.class))).thenReturn(quiz);
        when(quizRepository.save(any(Quiz.class))).thenReturn(quiz);
        when(quizMapper.toDto(any(Quiz.class))).thenReturn(quizDTO);

        QuizDTO createdQuiz = quizService.createQuiz(quizDTO);

        assertThat(createdQuiz).isNotNull();
        assertThat(createdQuiz.getQuizId()).isEqualTo(quiz.getQuizId());
        verify(quizRepository).save(any(Quiz.class));
    }

    @Test
    void verifyGetAllQuizzesReturnsAllQuizzes() {
        List<Quiz> quizzes = List.of(quiz);
        when(quizRepository.findAll()).thenReturn(quizzes);
        when(quizMapper.toDto(any(Quiz.class))).thenReturn(quizDTO);

        List<QuizDTO> retrievedQuizzes = quizService.getAllQuizzes();

        assertThat(retrievedQuizzes).isNotEmpty();
        assertThat(retrievedQuizzes.size()).isEqualTo(quizzes.size());
        verify(quizRepository).findAll();
    }

    @Test
    void verifyFindQuizByIdReturnsQuiz() {
        when(quizRepository.findById(anyLong())).thenReturn(Optional.of(quiz));

        Quiz result = quizService.findQuizById(quiz.getQuizId());

        assertThat(result).isNotNull();
        assertThat(result.getQuizId()).isEqualTo(quiz.getQuizId());
        verify(quizRepository).findById(quiz.getQuizId());
    }

    @Test
    void verifyFindQuizByIdReturnsNullWhenNotFound() {
        when(quizRepository.findById(anyLong())).thenReturn(Optional.empty());

        Quiz result = quizService.findQuizById(2L);

        assertThat(result).isNull();
        verify(quizRepository).findById(2L);
    }

    @Test
    void verifyGetQuizzesByCreatorIdReturnsQuizzes() {
        List<Quiz> quizzes = List.of(quiz);
        when(quizRepository.findByCreator_UserId(anyLong())).thenReturn(quizzes);

        List<Quiz> result = quizService.getQuizzesByCreatorId(user.getUserId());

        assertThat(result).isNotEmpty();
        assertThat(result.size()).isEqualTo(quizzes.size());
        verify(quizRepository).findByCreator_UserId(user.getUserId());
    }

    @Test
    void verifyGetQuizzesByCreatorIdAndQuizIdReturnsSpecificQuiz() {
        List<Quiz> quizzes = List.of(quiz);
        when(quizRepository.findByCreator_UserIdAndQuizId(anyLong(), anyLong())).thenReturn(quizzes);

        List<Quiz> result = quizService.getQuizzesByCreatorIdAndQuizId(user.getUserId(), quiz.getQuizId());

        assertThat(result).isNotEmpty();
        assertThat(result.get(0).getQuizId()).isEqualTo(quiz.getQuizId());
        verify(quizRepository).findByCreator_UserIdAndQuizId(user.getUserId(), quiz.getQuizId());
    }
}
