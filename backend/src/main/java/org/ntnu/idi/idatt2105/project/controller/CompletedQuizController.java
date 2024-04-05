package org.ntnu.idi.idatt2105.project.controller;

import org.ntnu.idi.idatt2105.project.dto.CompletedQuizDTO;
import org.ntnu.idi.idatt2105.project.dto.UserAnswerDTO;
import org.ntnu.idi.idatt2105.project.service.CompletedQuizService;
import org.ntnu.idi.idatt2105.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller class for handling completed quizzes.
 */
@RestController
@RequestMapping("/api/completed-quizzes")
@CrossOrigin(origins = "*")
public class CompletedQuizController {

  private final CompletedQuizService completedQuizService;
  private final UserService userService;

  @Autowired
  public CompletedQuizController(CompletedQuizService completedQuizService, UserService userService) {
      this.completedQuizService = completedQuizService;
      this.userService = userService;
  }

  /**
   * Endpoint for submitting answers to a quiz.
   *
   * @param quizId The id of the quiz
   * @param answers The answers submitted by the user
   * @return ResponseEntity with the completed quiz
   */
  @PostMapping("/{quizId}/submit")
  public ResponseEntity<CompletedQuizDTO> submitAnswers(
          @PathVariable Long quizId,
          @RequestBody List<UserAnswerDTO> answers) {

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String username = authentication.getName();

    int userId = userService.findIdByUsername(username);

    CompletedQuizDTO completedQuiz = completedQuizService.submitUserAnswers((long)userId, quizId, answers);
    return ResponseEntity.ok(completedQuiz);
  }

  /**
   * Endpoint for getting completed quizzes from a user.
   *
   * @param quizId The id of the quiz
   * @return ResponseEntity with a list of completed quizzes for the user
   */
  @GetMapping("/{quizId}")
  public ResponseEntity<List<CompletedQuizDTO>> getCompletedQuizzesForUser(@PathVariable Long quizId) {
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      String username = authentication.getName();

      int userId = userService.findIdByUsername(username);

      List<CompletedQuizDTO> completedQuizzes = completedQuizService.getCompletedQuizzesForUser((long)userId, quizId);
      return ResponseEntity.ok(completedQuizzes);
  }
}
