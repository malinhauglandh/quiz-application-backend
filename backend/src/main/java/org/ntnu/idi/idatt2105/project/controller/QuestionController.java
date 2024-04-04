package org.ntnu.idi.idatt2105.project.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.ntnu.idi.idatt2105.project.dto.QuestionChoiceDTO;
import org.ntnu.idi.idatt2105.project.dto.QuestionDTO;
import org.ntnu.idi.idatt2105.project.entity.Question;
import org.ntnu.idi.idatt2105.project.entity.QuestionChoice;
import org.ntnu.idi.idatt2105.project.service.QuestionService;
import org.ntnu.idi.idatt2105.project.service.QuestionTypeService;
import org.ntnu.idi.idatt2105.project.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/questions")
public class QuestionController {

  private final QuestionService questionService;
  private final QuizService quizService;
  private final QuestionTypeService questionTypeService;
  private final ObjectMapper objectMapper;


  @Autowired
  public QuestionController(QuestionService questionService, QuizService quizService, QuestionTypeService questionTypeService, ObjectMapper objectMapper) {
    this.questionService = questionService;
    this.quizService = quizService;
    this.questionTypeService = questionTypeService;
    this.objectMapper = objectMapper;
  }


  @PostMapping("/create")
  public ResponseEntity<QuestionDTO> createQuestion(
          @RequestParam("questionText") String questionText,
          @RequestParam("tag") String tag,
          @RequestParam("quizId") Long quizId,
          @RequestParam("questionTypeId") Long questionTypeId,
          @RequestParam("choices") String choicesJson,
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

    List<QuestionChoiceDTO> choiceDTOs = parseChoices(choicesJson);
    List<QuestionChoice> questionChoices = choiceDTOs.stream()
            .map(dto -> convertToQuestionChoiceEntity(dto, question))
            .toList();

    if (question.getQuestionChoiceList() == null) {
      question.setQuestionChoiceList(new ArrayList<>());
    }

    question.getQuestionChoiceList().clear();
    for (QuestionChoice choice : questionChoices) {
      choice.setQuestion(question);
    }
    question.getQuestionChoiceList().addAll(questionChoices);

    QuestionDTO createdQuestionDTO = questionService.createQuestion(question);
    return ResponseEntity.ok(createdQuestionDTO);
  }

  private List<QuestionChoiceDTO> parseChoices(String choicesJson) {
    try {
      return objectMapper.readValue(choicesJson, new TypeReference<>() {
      });
    } catch (IOException e) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unable to parse choices", e);
    }
  }

  private QuestionChoice convertToQuestionChoiceEntity(QuestionChoiceDTO dto, Question question) {
    QuestionChoice entity = new QuestionChoice();
    entity.setChoice(dto.getChoice());
    entity.setExplanation(dto.getExplanation());
    entity.setIsCorrectChoice(dto.isCorrectChoice());
    entity.setQuestion(question);
    return entity;
  }

  @GetMapping("/allQuestions")
  public ResponseEntity<List<Question>> getAllQuestions() {
    List<Question> questions = questionService.getAllQuestion();
    return ResponseEntity.ok(questions);
  }
}

