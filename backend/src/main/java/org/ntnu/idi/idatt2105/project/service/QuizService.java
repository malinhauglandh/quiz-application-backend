package org.ntnu.idi.idatt2105.project.service;

import java.util.List;
import java.util.stream.Collectors;

import jakarta.persistence.EntityNotFoundException;
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

@Service
public class QuizService {

    private final QuizRepository quizRepository;

    private final CategoryRepository categoryRepository;

    private final UserRepository userRepository;

    private final QuizMapper quizMapper;

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

    public List<QuizDTO> getAllQuizzes() {
        List<Quiz> quizzes = quizRepository.findAll();

        return quizzes.stream()
                .map(quizMapper::toDto)
                .collect(Collectors.toList());
    }

  public Quiz findQuizById(Long quizId) {
    return quizRepository.findById(quizId).orElse(null);
    }

    public List<QuizDTO> getQuizzesByUserId(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("User not found"));

        List<Quiz> quizzes = quizRepository.getAllByCreator(user);

        return quizzes.stream()
                .map(quizMapper::toDto)
                .collect(Collectors.toList());
    }
}
