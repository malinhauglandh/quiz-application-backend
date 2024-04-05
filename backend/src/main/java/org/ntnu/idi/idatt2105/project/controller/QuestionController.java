package org.ntnu.idi.idatt2105.project.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.ntnu.idi.idatt2105.project.dto.QuestionChoiceDTO;
import org.ntnu.idi.idatt2105.project.dto.QuestionDTO;
import org.ntnu.idi.idatt2105.project.entity.Question;
import org.ntnu.idi.idatt2105.project.entity.QuestionChoice;
import org.ntnu.idi.idatt2105.project.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Controller for handling HTTP requests for questions.
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/questions")
public class QuestionController {

  /**
   * The service class for the question controller.
   */
  private final QuestionService questionService;

  /**
   * The service class for the quiz controller.
   */
  private final QuizService quizService;

  /**
   * The service class for the question type controller.
   */
  private final QuestionTypeService questionTypeService;

  /**
   * The mapper class for the question choice controller.
   */
  private final QuestionChoiceMapper questionChoiceMapper;


  /**
   * Creates a new question controller with the specified services.
   * @param questionService questionService
   * @param quizService quizService
   * @param questionTypeService questionTypeService
   * @param objectMapper objectMapper
   * @param questionChoiceMapper questionChoiceMapper
   */
  @Autowired
  public QuestionController(QuestionService questionService, QuizService quizService, QuestionTypeService questionTypeService, ObjectMapper objectMapper, QuestionChoiceMapper questionChoiceMapper) {
    this.questionService = questionService;
    this.quizService = quizService;
    this.questionTypeService = questionTypeService;
    this.questionChoiceMapper = questionChoiceMapper;
  }

  /**
   * Creates a new question with the specified question text, tag, quiz id, question type id, choices and file.
   * @param questionText questionText
   * @param tag tag
   * @param quizId quizId
   * @param questionTypeId questionTypeId
   * @param choices choices
   * @param file file
   * @return questionDTO
   */
  @PostMapping("/create")
  public ResponseEntity<QuestionDTO> createQuestion(
          @RequestParam("questionText") String questionText,
          @RequestParam("tag") String tag,
          @RequestParam("quizId") Long quizId,
          @RequestParam("questionTypeId") Long questionTypeId,
          @RequestParam ("choices") String choices,
          @RequestParam(value = "file", required = false) MultipartFile file) {

    Question question = new Question();
    question.setQuestionText(questionText);
    question.setTag(tag);
    question.setQuiz(quizService.findQuizById(quizId));
    question.setQuestionType(questionTypeService.findTypeByID(questionTypeId));

    if (file != null && !file.isEmpty()) {
      String fileName = file.getOriginalFilename();
      question.setMultimedia(fileName);
    }

    questionService.createQuestion(question);
    List<QuestionChoiceDTO> choiceDTOs = questionService.parseChoices(choices);
    question.getQuestionChoiceList().clear();
    List<QuestionChoice> mappedChoices = choiceDTOs.stream()
            .map(dto -> questionChoiceMapper.questionChoiceDTOToQuestionChoice(dto, question))
            .toList();
    question.getQuestionChoiceList().addAll(mappedChoices);
    QuestionDTO createdQuestionDTO = questionService.createQuestion(question);
    return ResponseEntity.ok(createdQuestionDTO);
  }

  /**
   * Get all questions.
   * @return list of questions
   */
  @GetMapping("/allQuestions")
  public ResponseEntity<List<QuestionDTO>> getAllQuestions() {
    List<QuestionDTO> questions = questionService.getAllQuestions();
    return ResponseEntity.ok(questions);
  }
}

