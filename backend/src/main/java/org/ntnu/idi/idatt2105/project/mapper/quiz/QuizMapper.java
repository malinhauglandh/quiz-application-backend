package org.ntnu.idi.idatt2105.project.mapper.quiz;

import org.ntnu.idi.idatt2105.project.dto.quiz.CreateQuizDTO;
import org.ntnu.idi.idatt2105.project.dto.quiz.QuizWithQuestionsDTO;
import org.ntnu.idi.idatt2105.project.entity.quiz.Quiz;
import org.ntnu.idi.idatt2105.project.mapper.question.QuestionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

/** Mapper for the entity Quiz and its DTO QuizDTO. This class also
 * maps the entity Quiz to a DTO including its questions  */
@Component
public class QuizMapper {

    /**
     * Mapper for the Question entity
     */
    private final QuestionMapper questionMapper;

    /**
     * Constructor for QuizMapper
     * @param questionMapper The QuestionMapper
     */
    @Autowired
    public QuizMapper(QuestionMapper questionMapper) {
        this.questionMapper = questionMapper;
    }

    /**
     * Maps a Quiz object to a QuizDTO object
     *
     * @param quiz The Quiz object to map
     * @return The QuizDTO object
     */
    public CreateQuizDTO toDto(Quiz quiz) {
        if (quiz == null) {
            return null;
        }

        CreateQuizDTO dto = new CreateQuizDTO();
        mapCommonFields(dto, quiz);
        return dto;
    }

    /**
     * Maps a QuizDTO object to a Quiz object
     *
     * @param dto The QuizDTO object to map
     * @return The Quiz object
     */
    public Quiz toEntity(CreateQuizDTO dto) {
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

    /**
     * Maps a Quiz object to a QuizWithQuestionsDTO object
     *
     * @param quiz The Quiz object to map
     * @return The QuizWithQuestionsDTO object
     */
    public QuizWithQuestionsDTO toQuizWithQuestionsDTO(Quiz quiz) {
        if (quiz == null) {
            return null;
        }
        QuizWithQuestionsDTO dto = new QuizWithQuestionsDTO();
        mapCommonFields(dto, quiz);

        if (quiz.getQuestionList() != null) {
            dto.setQuestions(quiz.getQuestionList().stream()
                    .map(questionMapper::questionToQuestionDTO)
                    .collect(Collectors.toList()));
        }
        return dto;
    }

    /**
     * Maps common fields from a Quiz object to a QuizDTO object
     * @param dto The QuizDTO object
     * @param quiz The Quiz object
     */
    private void mapCommonFields(CreateQuizDTO dto, Quiz quiz) {
        dto.setQuizId(quiz.getQuizId());
        dto.setQuizName(quiz.getQuizName());
        dto.setQuizDescription(quiz.getQuizDescription());
        dto.setMultimedia(quiz.getMultimedia());
        dto.setDifficultyLevel(quiz.getDifficultyLevel());

        if (quiz.getCategory() != null) dto.setCategoryId(quiz.getCategory().getCategory_id());
        if (quiz.getCreator() != null) dto.setCreatorId(quiz.getCreator().getUserId());
    }
}
