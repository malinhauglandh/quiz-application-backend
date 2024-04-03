package org.ntnu.idi.idatt2105.project.controller;

import java.util.List;
import org.ntnu.idi.idatt2105.project.dto.QuizDTO;
import org.ntnu.idi.idatt2105.project.entity.Category;
import org.ntnu.idi.idatt2105.project.entity.Quiz;
import org.ntnu.idi.idatt2105.project.entity.User;
import org.ntnu.idi.idatt2105.project.service.CategoryService;
import org.ntnu.idi.idatt2105.project.service.QuizService;
import org.ntnu.idi.idatt2105.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("api/quizzes")
@CrossOrigin(origins = "*")
public class QuizController {

    private final QuizService quizService;
    private final CategoryService categoryService;
    private final UserService userService;

    @Autowired
    public QuizController(
            QuizService quizService, CategoryService categoryService, UserService userService) {
        this.quizService = quizService;
        this.categoryService = categoryService;
        this.userService = userService;
    }

    @PostMapping("/createquiz")
    public ResponseEntity<QuizDTO> createQuiz(
            @RequestParam("quizName") String quizName,
            @RequestParam("quizDescription") String quizDescription,
            @RequestParam("difficultyLevel") String difficultyLevel,
            @RequestParam("category") Long categoryId,
            @RequestParam("creator") Long creatorId,
            @RequestParam(value = "file", required = false) MultipartFile file) {

        Quiz quiz = new Quiz();
        quiz.setQuizName(quizName);
        quiz.setQuizDescription(quizDescription);
        quiz.setDifficultyLevel(difficultyLevel);

        if (file != null && !file.isEmpty()) {
            String fileName = file.getOriginalFilename();
            quiz.setMultimedia(fileName);
        }

        Category category =
                categoryService
                        .findCategoryById(categoryId)
                        .orElseThrow(
                                () ->
                                        new ResponseStatusException(
                                                HttpStatus.NOT_FOUND, "Category not found"));
        quiz.setCategory(category);

        User creator =
                userService
                        .findUserById(creatorId)
                        .orElseThrow(
                                () ->
                                        new ResponseStatusException(
                                                HttpStatus.NOT_FOUND, "User not found"));
        quiz.setCreator(creator);

        QuizDTO createdQuizDTO = quizService.createQuiz(quiz);
        return ResponseEntity.ok(createdQuizDTO);
    }

    @GetMapping
    public ResponseEntity<List<Quiz>> getAllQuizzes() {
        List<Quiz> quizzes = quizService.getAllQuizzes();
        return ResponseEntity.ok(quizzes);
    }
}
