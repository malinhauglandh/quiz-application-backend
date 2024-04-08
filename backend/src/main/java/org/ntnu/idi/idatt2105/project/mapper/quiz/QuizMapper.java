package org.ntnu.idi.idatt2105.project.mapper.quiz;

import org.ntnu.idi.idatt2105.project.dto.quiz.QuizDTO;
import org.ntnu.idi.idatt2105.project.entity.quiz.Quiz;
import org.springframework.stereotype.Component;

/** Mapper for the entity Quiz and its DTO QuizDTO. */
@Component
public class QuizMapper {

    /**
     * Maps a Quiz object to a QuizDTO object
     *
     * @param quiz The Quiz object to map
     * @return The QuizDTO object
     */
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

        if (quiz.getCategory() != null) dto.setCategoryId(quiz.getCategory().getCategory_id());
        if (quiz.getCreator() != null) dto.setCreatorId(quiz.getCreator().getUserId());
        return dto;
    }

    /**
     * Maps a QuizDTO object to a Quiz object
     *
     * @param dto The QuizDTO object to map
     * @return The Quiz object
     */
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
