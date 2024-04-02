package org.ntnu.idi.idatt2105.project.controller;

import org.ntnu.idi.idatt2105.project.entity.Question;
import org.ntnu.idi.idatt2105.project.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/questions")
public class QuestionController {

  private final QuestionService questionService;

  @Autowired
  public QuestionController(QuestionService questionService) {
    this.questionService = questionService;
  }

  @PostMapping("/add")
  public ResponseEntity<Question> addQuestion(@RequestBody Question question) {
    Question addedQuestion = questionService.addQuestion(question);
    return ResponseEntity.ok(addedQuestion);
  }

  @PutMapping("/{questionId}/update")
  public ResponseEntity<Question> updateQuestion(@PathVariable Long questionId, @RequestBody Question question) {
    Question updatedQuestion = questionService.updateQuestion(question); // Set the ID to questionId
    return ResponseEntity.ok(updatedQuestion);
  }

  @DeleteMapping("/{questionId}/delete")
  public ResponseEntity<?> deleteQuestion(@PathVariable Long questionId) {
    questionService.deleteQuestion(questionId);
    return ResponseEntity.ok().build();
  }

  @GetMapping
  public ResponseEntity<List<Question>> getAllQuestions() {
    List<Question> questions = questionService.findAllQuestions();
    return ResponseEntity.ok(questions);
  }
}