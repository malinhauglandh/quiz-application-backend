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

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/questions")
public class QuestionController {

  private final QuestionService questionService;
  private final QuizService quizService;
  private final QuestionTypeService questionTypeService;
  private final QuestionChoiceMapper questionChoiceMapper;


  @Autowired
  public QuestionController(QuestionService questionService, QuizService quizService, QuestionTypeService questionTypeService, ObjectMapper objectMapper, QuestionChoiceMapper questionChoiceMapper) {
    this.questionService = questionService;
    this.quizService = quizService;
    this.questionTypeService = questionTypeService;
    this.questionChoiceMapper = questionChoiceMapper;
  }


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
    question.getQuestionChoiceList().clear(); // Clear the collection to avoid duplicates
    List<QuestionChoice> mappedChoices = choiceDTOs.stream()
            .map(dto -> questionChoiceMapper.questionChoiceDTOToQuestionChoice(dto, question))
            .toList();
    question.getQuestionChoiceList().addAll(mappedChoices); // Add the new elements to the original collection
    QuestionDTO createdQuestionDTO = questionService.createQuestion(question);
    return ResponseEntity.ok(createdQuestionDTO);
  }

  @GetMapping("/allQuestions")
  public ResponseEntity<List<QuestionDTO>> getAllQuestions() {
    List<QuestionDTO> questions = questionService.getAllQuestions();
    return ResponseEntity.ok(questions);
  }
}

