package org.ntnu.idi.idatt2105.project.service;

import org.ntnu.idi.idatt2105.project.dto.QuestionChoiceDTO;
import org.ntnu.idi.idatt2105.project.entity.Question;
import org.ntnu.idi.idatt2105.project.entity.QuestionChoice;
import org.springframework.stereotype.Component;

@Component
public class QuestionChoiceMapper {

    public QuestionChoiceDTO questionChoiceToQuestionChoiceDTO(QuestionChoice choice) {
        QuestionChoiceDTO choiceDTO = new QuestionChoiceDTO();
        choiceDTO.setQuizChoiceId(choice.getQuizChoiceId());
        choiceDTO.setChoice(choice.getChoice());
        choiceDTO.setExplanation(choice.getExplanation());
        choiceDTO.setCorrectChoice(choice.isCorrectChoice());
        return choiceDTO;
    }

    public QuestionChoice questionChoiceDTOToQuestionChoice(QuestionChoiceDTO dto, Question question) {
        return new QuestionChoice(dto.getQuizChoiceId(), dto.getChoice(), dto.getExplanation(), dto.isCorrectChoice(), question);
    }
}
