package org.ntnu.idi.idatt2105.project.mapper.question;

import java.util.stream.Collectors;
import org.ntnu.idi.idatt2105.project.dto.question.QuestionDTO;
import org.ntnu.idi.idatt2105.project.entity.question.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/** Mapper class for mapping between Question and QuestionDTO */
@Component
public class QuestionMapper {

    private final QuestionChoiceMapper questionChoiceMapper;

    @Autowired
    public QuestionMapper(QuestionChoiceMapper questionChoiceMapper) {
        this.questionChoiceMapper = questionChoiceMapper;
    }

    /**
     * Maps a Question object to a QuestionDTO object
     *
     * @param question The Question object to map
     * @return The QuestionDTO object
     */
    public QuestionDTO questionToQuestionDTO(Question question) {
        QuestionDTO questionDTO = new QuestionDTO();
        questionDTO.setQuestionId(question.getQuestionId());
        questionDTO.setQuestionText(question.getQuestionText());
        questionDTO.setTag(question.getTag());
        questionDTO.setMultimedia(question.getMultimedia());
        questionDTO.setQuizId(question.getQuiz().getQuizId());
        questionDTO.setQuestionTypeId(question.getQuestionType().getTypeId());
        questionDTO.setChoices(
                question.getQuestionChoiceList().stream()
                        .map(questionChoiceMapper::questionChoiceToQuestionChoiceDTO)
                        .collect(Collectors.toList()));
        return questionDTO;
    }

    /**
     * Maps a QuestionDTO object to a Question object
     *
     * @param questionDTO The QuestionDTO object to map
     * @return The Question object
     */
    public Question questionDTOToQuestion(QuestionDTO questionDTO) {
        Question question = new Question();
        question.setQuestionId(questionDTO.getQuestionId());
        question.setQuestionText(questionDTO.getQuestionText());
        question.setTag(questionDTO.getTag());
        question.setMultimedia(questionDTO.getMultimedia());
        question.setQuestionChoiceList(
                questionDTO.getChoices().stream()
                        .map(
                                choiceDTO ->
                                        questionChoiceMapper.questionChoiceDTOToQuestionChoice(
                                                choiceDTO, question))
                        .collect(Collectors.toList()));
        return question;
    }
}
