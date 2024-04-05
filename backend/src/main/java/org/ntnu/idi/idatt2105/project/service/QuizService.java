package org.ntnu.idi.idatt2105.project.service;

import java.util.List;
import org.ntnu.idi.idatt2105.project.dto.QuizDTO;
import org.ntnu.idi.idatt2105.project.entity.Quiz;
import org.ntnu.idi.idatt2105.project.mapper.QuizMapper;
import org.ntnu.idi.idatt2105.project.repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuizService {

    private final QuizRepository quizRepository;
    private final QuizMapper quizMapper;

    @Autowired
    public QuizService(QuizRepository quizRepository, QuizMapper quizMapper) {
        this.quizRepository = quizRepository;
        this.quizMapper = quizMapper;
    }

    public QuizDTO createQuiz(Quiz quiz) {
        Quiz createdQuiz = quizRepository.save(quiz);
        return quizMapper.convertToQuizDTO(createdQuiz);
    }

    public List<Quiz> getAllQuizzes() {
        return quizRepository.findAll();
    }

    public List<Quiz> getQuizzesByCreatorId(Long creatorId) {
        return quizRepository.findByCreator_UserId(creatorId);
    }
}
