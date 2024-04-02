package org.ntnu.idi.idatt2105.project.controller;

import org.ntnu.idi.idatt2105.project.entity.Question;
import org.ntnu.idi.idatt2105.project.entity.Quiz;
import org.ntnu.idi.idatt2105.project.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/quizzes")
public class QuizController {

  private final QuizService quizService;

  @Autowired
  public QuizController(QuizService quizService) {
    this.quizService = quizService;
  }

  @PostMapping("/createquiz")
  public ResponseEntity<Quiz> createQuiz(@RequestBody Quiz quiz) {
    Quiz createdQuiz = quizService.createQuiz(quiz);
    return ResponseEntity.ok(createdQuiz);
  }

  @PostMapping("/{quizId}/questions")
  public ResponseEntity<?> addQuestionToQuiz(@PathVariable Long quizId, @RequestBody Question question) {
    return ResponseEntity.ok().build();
  }

  @GetMapping
  public ResponseEntity<List<Quiz>> getAllQuizzes() {
    List<Quiz> quizzes = quizService.getAllQuizzes();
    return ResponseEntity.ok(quizzes);
  }
}