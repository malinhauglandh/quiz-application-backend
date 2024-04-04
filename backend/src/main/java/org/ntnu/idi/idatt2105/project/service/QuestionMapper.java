package org.ntnu.idi.idatt2105.project.service;

import org.ntnu.idi.idatt2105.project.dto.QuestionDTO;
import org.ntnu.idi.idatt2105.project.entity.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class QuestionMapper {

    private final QuestionChoiceMapper questionChoiceMapper;

    @Autowired
    public QuestionMapper(QuestionChoiceMapper questionChoiceMapper) {
        this.questionChoiceMapper = questionChoiceMapper;
    }

    public QuestionDTO questionToQuestionDTO(Question question) {
        QuestionDTO questionDTO = new QuestionDTO();
        questionDTO.setQuestionId(question.getQuestionId());
        questionDTO.setQuestionText(question.getQuestionText());
        questionDTO.setTag(question.getTag());
        questionDTO.setMultimedia(question.getMultimedia());
        questionDTO.setQuizId(question.getQuiz().getQuizId());
        questionDTO.setQuestionTypeId(question.getQuestionType().getTypeId());
        questionDTO.setChoices(question.getQuestionChoiceList().stream()
                .map(questionChoiceMapper::questionChoiceToQuestionChoiceDTO)
                .collect(Collectors.toList()));
        return questionDTO;
    }
    public Question questionDTOToQuestion(QuestionDTO questionDTO) {
        Question question = new Question();
        question.setQuestionId(questionDTO.getQuestionId());
        question.setQuestionText(questionDTO.getQuestionText());
        question.setTag(questionDTO.getTag());
        question.setMultimedia(questionDTO.getMultimedia());
        question.setQuestionChoiceList(questionDTO.getChoices().stream()
                .map(choiceDTO -> questionChoiceMapper.questionChoiceDTOToQuestionChoice(choiceDTO, question))
                .collect(Collectors.toList()));
        return question;
    }
}