package org.ntnu.idi.idatt2105.project.mapper;

import org.ntnu.idi.idatt2105.project.dto.QuizDTO;
import org.ntnu.idi.idatt2105.project.entity.Quiz;
import org.springframework.stereotype.Component;

@Component
public class QuizMapper {
    public QuizDTO convertToQuizDTO(Quiz quiz) {
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
}
