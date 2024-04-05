package org.ntnu.idi.idatt2105.project.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.ntnu.idi.idatt2105.project.dto.QuestionChoiceDTO;
import org.ntnu.idi.idatt2105.project.dto.QuestionDTO;
import org.ntnu.idi.idatt2105.project.entity.Question;
import org.ntnu.idi.idatt2105.project.entity.QuestionChoice;
import org.ntnu.idi.idatt2105.project.repository.QuestionRepository;
import org.ntnu.idi.idatt2105.project.repository.QuestionChoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class for the Question entity.
 */

@Service
public class QuestionService {

  /**
   * Repository for Question
   */
  private final QuestionRepository questionRepository;

  /**
   * Mapper for Question
   */
  private final QuestionMapper questionMapper;

  /**
   * Object mapper
   */
  private final ObjectMapper objectMapper;

  /**
   * Constructor for QuestionService.
   * @param questionRepository questionRepository
   * @param questionMapper questionMapper
   * @param objectMapper objectMapper
   */

  @Autowired
  public QuestionService(QuestionRepository questionRepository,
                         QuestionMapper questionMapper,
                         ObjectMapper objectMapper) {
      this.questionRepository = questionRepository;
      this.questionMapper = questionMapper;
      this.objectMapper = objectMapper;
  }

  /**
   * Create a new question.
   * @param question Question object
   * @return QuestionDTO object
   */
  @Transactional
  public QuestionDTO createQuestion(Question question) {
    Question q = questionRepository.save(question);
    return questionMapper.questionToQuestionDTO(q);
  }

  /**
   * Get all questions.
   * @return List of QuestionDTO objects
   */
  public List<QuestionDTO> getAllQuestions() {
    return questionRepository.findAll().stream().map(questionMapper::questionToQuestionDTO).collect(Collectors.toList());
  }

  /**
   * Find a question by its ID.
   * @param questionId Question ID
   * @return Question object
   */
  public Question findQuestionById(Long questionId) {
    return questionRepository.findById(questionId).orElse(null);
  }

  /**
   * 'choices' is a JSON string representing a list of QuestionChoiceDTO objects.
   * @param choicesJson JSON string representing a list of QuestionChoiceDTO objects
   * @return List of QuestionChoiceDTO objects
   */
  public List<QuestionChoiceDTO> parseChoices(String choicesJson) {
    try {
      return  objectMapper.readValue(choicesJson, new TypeReference<>() {
      });
    } catch (IOException e) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unable to parse choices", e);
    }
  }
}
