package org.ntnu.idi.idatt2105.project.service;

import org.ntnu.idi.idatt2105.project.dto.QuestionChoiceDTO;
import org.ntnu.idi.idatt2105.project.dto.QuestionDTO;
import org.ntnu.idi.idatt2105.project.entity.Question;
import org.ntnu.idi.idatt2105.project.entity.QuestionChoice;
import org.ntnu.idi.idatt2105.project.repository.QuestionRepository;
import org.ntnu.idi.idatt2105.project.repository.QuestionChoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class QuestionService {

  private final QuestionRepository questionRepository;

  private final QuestionChoiceRepository questionChoiceRepository;


  @Autowired
    public QuestionService(QuestionRepository questionRepository, QuestionChoiceRepository questionChoiceRepository, QuizService quizService, QuestionTypeService questionTypeService) {
        this.questionRepository = questionRepository;
        this.questionChoiceRepository = questionChoiceRepository;
    }

  public QuestionDTO createQuestion(Question question) {
    Question createdQuestion = questionRepository.save(question);
    return convertToQuestionDTO(createdQuestion);
  }

  private QuestionDTO convertToQuestionDTO(Question question) {
    QuestionDTO dto = new QuestionDTO();
    dto.setQuestionId((long) question.getQuestionId());
    dto.setQuestionText(question.getQuestionText());
    dto.setTag(question.getTag());
    dto.setMultimedia(question.getMultimedia());
    dto.setQuizId((long) question.getQuiz().getQuizId());
    dto.setQuestionTypeId((long) question.getQuestionType().getTypeId());
    List<QuestionChoice> choices = questionChoiceRepository.findAllByQuestion(question);
    for (QuestionChoice choice : choices) {
      QuestionChoiceDTO choiceDTO = new QuestionChoiceDTO();
      choiceDTO.setQuizChoiceId((long) choice.getQuizChoiceId());
      choiceDTO.setExplanation(choice.getExplanation());
      choiceDTO.setIsCorrectChoice(choice.isCorrectChoice());
      dto.addChoice(choiceDTO);
    }
    return dto;
  }

  public List<Question> getAllQuestion() {
    return questionRepository.findAll();
  }

  public Question findQuestionById(Long questionId) {
    return questionRepository.findById(questionId).orElse(null);
  }

  @Transactional
  public void saveQuestionChoices(List<QuestionChoice> questionChoices) {
    questionChoiceRepository.saveAll(questionChoices);
  }
}
