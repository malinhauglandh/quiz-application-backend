package org.ntnu.idi.idatt2105.project.service;

import org.ntnu.idi.idatt2105.project.dto.QuizDTO;
import org.ntnu.idi.idatt2105.project.entity.Quiz;
import org.springframework.stereotype.Component;

@Component
public class QuizMapper {

    public QuizDTO toDto(Quiz quiz) {
        if (quiz == null) {
            return null;
        }

        QuizDTO dto = new QuizDTO();
        dto.setQuizId(quiz.getQuizId());
        dto.setQuizName(quiz.getQuizName());
        dto.setQuizDescription(quiz.getQuizDescription());
        dto.setMultimedia(quiz.getMultimedia());
        dto.setDifficultyLevel(quiz.getDifficultyLevel());

        if(quiz.getCategory() != null)
            dto.setCategoryId(quiz.getCategory().getCategoryId());
        if(quiz.getCreator() != null)
            dto.setCreatorId(quiz.getCreator().getUserId());
        return dto;
    }

    public Quiz toEntity(QuizDTO dto) {
        if (dto == null) {
            return null;
        }

        Quiz quiz = new Quiz();
        quiz.setQuizId(dto.getQuizId());
        quiz.setQuizName(dto.getQuizName());
        quiz.setQuizDescription(dto.getQuizDescription());
        quiz.setMultimedia(dto.getMultimedia());
        quiz.setDifficultyLevel(dto.getDifficultyLevel());

        return quiz;
    }
}

