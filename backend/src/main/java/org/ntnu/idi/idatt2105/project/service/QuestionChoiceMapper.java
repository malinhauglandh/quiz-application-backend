package org.ntnu.idi.idatt2105.project.service;

import org.ntnu.idi.idatt2105.project.dto.QuestionChoiceDTO;
import org.ntnu.idi.idatt2105.project.entity.Question;
import org.ntnu.idi.idatt2105.project.entity.QuestionChoice;
import org.springframework.stereotype.Component;

/**
 * Mapper class for mapping between QuestionChoice and QuestionChoiceDTO
 */
@Component
public class QuestionChoiceMapper {

    /**
     * Maps a QuestionChoice object to a QuestionChoiceDTO object
     * @param choice The QuestionChoice object to map
     * @return The QuestionChoiceDTO object
     */
    public QuestionChoiceDTO questionChoiceToQuestionChoiceDTO(QuestionChoice choice) {
        QuestionChoiceDTO choiceDTO = new QuestionChoiceDTO();
        choiceDTO.setQuizChoiceId(choice.getQuizChoiceId());
        choiceDTO.setChoice(choice.getChoice());
        choiceDTO.setExplanation(choice.getExplanation());
        choiceDTO.setCorrectChoice(choice.isCorrectChoice());
        return choiceDTO;
    }

    /**
     * Maps a QuestionChoiceDTO object to a QuestionChoice object
     * @param dto The QuestionChoiceDTO object to map
     * @param question The Question object
     * @return The QuestionChoice object
     */
    public QuestionChoice questionChoiceDTOToQuestionChoice(QuestionChoiceDTO dto, Question question) {
        return new QuestionChoice(dto.getQuizChoiceId(), dto.getChoice(), dto.getExplanation(), dto.isCorrectChoice(), question);
    }
}
