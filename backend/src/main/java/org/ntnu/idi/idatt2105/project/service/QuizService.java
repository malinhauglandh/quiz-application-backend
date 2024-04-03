package org.ntnu.idi.idatt2105.project.service;

import java.util.List;
import org.ntnu.idi.idatt2105.project.dto.QuizDTO;
import org.ntnu.idi.idatt2105.project.entity.Quiz;
import org.ntnu.idi.idatt2105.project.repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuizService {

    private final QuizRepository quizRepository;

    @Autowired
    public QuizService(QuizRepository quizRepository) {
        this.quizRepository = quizRepository;
    }

    public QuizDTO createQuiz(Quiz quiz) {
        Quiz createdQuiz = quizRepository.save(quiz);
        return convertToQuizDTO(createdQuiz);
    }

    private QuizDTO convertToQuizDTO(Quiz quiz) {
        QuizDTO dto = new QuizDTO();
        dto.setQuizId((long) quiz.getQuizId());
        dto.setQuizName(quiz.getQuizName());
        dto.setQuizDescription(quiz.getQuizDescription());
        dto.setDifficultyLevel(quiz.getDifficultyLevel());
        dto.setMultimedia(quiz.getMultimedia());
        dto.setCategoryId((long) quiz.getCategory().getCategoryId());
        dto.setCreatorId((long) quiz.getCreator().getUserId());
        return dto;
    }

    public List<Quiz> getAllQuizzes() {
        return quizRepository.findAll();
    }
}
