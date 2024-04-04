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

    @PostMapping("/createQuiz")
    public ResponseEntity<QuizDTO> createQuiz(
            @RequestParam("quizName") String quizName,
            @RequestParam("quizDescription") String quizDescription,
            @RequestParam("difficultyLevel") String difficultyLevel,
            @RequestParam("category") Long categoryId,
            @RequestParam("creator") Long creatorId,
            @RequestParam(value = "file", required = false) MultipartFile file) {

        QuizDTO quizDTO = new QuizDTO();
        quizDTO.setQuizName(quizName);
        quizDTO.setQuizDescription(quizDescription);
        quizDTO.setDifficultyLevel(difficultyLevel);
        quizDTO.setCategoryId(categoryId);
        quizDTO.setCreatorId(creatorId);

        if (file != null && !file.isEmpty()) {
            String fileName = file.getOriginalFilename();
            quizDTO.setMultimedia(fileName);
        }

        QuizDTO createdQuizDTO = quizService.createQuiz(quizDTO);
        return ResponseEntity.ok(createdQuizDTO);
    }

    @GetMapping
    public ResponseEntity<List<QuizDTO>> getAllQuizzes() {
        List<QuizDTO> quizzes = quizService.getAllQuizzes();
        return ResponseEntity.ok(quizzes);
    }

    // get quizzes by userid
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<QuizDTO>> getQuizzesByUserId(@PathVariable Long userId) {
        List<QuizDTO> quizzes = quizService.getQuizzesByUserId(userId);
        return ResponseEntity.ok(quizzes);
    }
}
