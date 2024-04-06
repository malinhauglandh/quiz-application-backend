package org.ntnu.idi.idatt2105.project.controller.quiz;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;

import jakarta.persistence.EntityNotFoundException;
import org.ntnu.idi.idatt2105.project.dto.quiz.CompletedQuizDTO;
import org.ntnu.idi.idatt2105.project.dto.user.UserAnswerDTO;
import org.ntnu.idi.idatt2105.project.service.quiz.CompletedQuizService;
import org.ntnu.idi.idatt2105.project.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

/** Controller class for handling completed quizzes. */
@RestController
@RequestMapping("/api/completed-quizzes")
@CrossOrigin(origins = "*")
@Tag(name = "Completed Quiz Management", description = "Endpoints for managing completed quizzes")
public class CompletedQuizController {

    private final CompletedQuizService completedQuizService;
    private final UserService userService;

    @Autowired
    public CompletedQuizController(
            CompletedQuizService completedQuizService, UserService userService) {
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
    @Operation(
            summary = "Submit answers to a quiz",
            parameters = {
                @Parameter(name = "quizId", description = "The id of the quiz"),
                @Parameter(name = "answers", description = "The answers submitted by the user")
            },
            responses = {
                @ApiResponse(responseCode = "200", description = "Answers submitted"),
                @ApiResponse(responseCode = "400", description = "Invalid input")
            })
    @PostMapping("/{quizId}/submit")
    public ResponseEntity<CompletedQuizDTO> submitAnswers(
            @PathVariable Long quizId, @RequestBody List<UserAnswerDTO> answers) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Long userId = userService.findIdByUsername(username);

        CompletedQuizDTO completedQuiz =
                completedQuizService.submitUserAnswers(userId, quizId, answers);
        return ResponseEntity.ok(completedQuiz);
    }

    /**
     * Endpoint for getting the latest completed quiz attempt by a user for a specific quiz.
     *
     * @param quizId The id of the quiz
     * @return ResponseEntity with the latest completed quiz attempt for the user
     */
    @Operation(
            summary = "Get the latest completed quiz for a user",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Latest completed quiz found"),
                    @ApiResponse(responseCode = "404", description = "Completed quizzes not found")
            })
    @GetMapping("/{quizId}")
    public ResponseEntity<CompletedQuizDTO> getLatestCompletedQuizForUser(
            @PathVariable Long quizId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Long userId = userService.findIdByUsername(username);

        try {
            CompletedQuizDTO latestCompletedQuiz =
                    completedQuizService.getLatestCompletedQuizForUser(userId, quizId);
            return ResponseEntity.ok(latestCompletedQuiz);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
