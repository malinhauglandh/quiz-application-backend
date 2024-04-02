package org.ntnu.idi.idatt2105.project.controller;

import org.ntnu.idi.idatt2105.project.entity.Question;
import org.ntnu.idi.idatt2105.project.entity.QuestionChoice;
import org.ntnu.idi.idatt2105.project.service.QuestionChoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/questions/{questionId}/choices")
public class QuestionChoiceController {

  private final QuestionChoiceService questionChoiceService;

  @Autowired
  public QuestionChoiceController(QuestionChoiceService questionChoiceService) {
    this.questionChoiceService = questionChoiceService;
  }

  @PostMapping
  public ResponseEntity<QuestionChoice> addChoiceToQuestion(@PathVariable Long questionId, @RequestBody QuestionChoice choice) {
    choice.setQuestion(new Question(questionId));
    QuestionChoice addedChoice = questionChoiceService.addQuestionChoice(choice);
    return ResponseEntity.ok(addedChoice);
  }

  @GetMapping
  public ResponseEntity<List<QuestionChoice>> getChoicesForQuestion(@PathVariable Long questionId) {
    List<QuestionChoice> choices = questionChoiceService.getChoicesByQuestionId(questionId);
    return ResponseEntity.ok(choices);
  }

  // Additional endpoints for updating and deleting question choices
}