package org.ntnu.idi.idatt2105.project.controller;

import java.util.List;
import java.util.stream.Collectors;
import org.ntnu.idi.idatt2105.project.dto.QuizDTO;
import org.ntnu.idi.idatt2105.project.entity.Category;
import org.ntnu.idi.idatt2105.project.entity.Quiz;
import org.ntnu.idi.idatt2105.project.entity.User;
import org.ntnu.idi.idatt2105.project.service.CategoryService;
import org.ntnu.idi.idatt2105.project.service.FileStorageService;
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
    private final FileStorageService fileStorageService;

    @Autowired
    public QuizController(
            QuizService quizService,
            CategoryService categoryService,
            UserService userService,
            FileStorageService fileStorageService) {
        this.quizService = quizService;
        this.categoryService = categoryService;
        this.userService = userService;
        this.fileStorageService = fileStorageService;
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

    @PostMapping("/upload/{quizId}")
    public ResponseEntity<String> uploadFile(
            @RequestParam("file") MultipartFile file, @PathVariable int quizId) {
        try {
            fileStorageService.storeFile(file, quizId);
            return ResponseEntity.ok().body("File uploaded successfully and linked to the quiz!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to upload file: " + e.getMessage());
        }
    }

    @GetMapping("/files/{fileName:.+}")
    public ResponseEntity<byte[]> getFile(@PathVariable String fileName) {
        return fileStorageService.loadFile(fileName);
    }

    @GetMapping("/user/{creatorId}")
    public ResponseEntity<List<QuizDTO>> getQuizzesByCreator(@PathVariable Long creatorId) {
        List<Quiz> quizzes = quizService.getQuizzesByCreatorId(creatorId);
        List<QuizDTO> quizDTOs =
                quizzes.stream().map(this::convertToQuizDTO).collect(Collectors.toList());
        return ResponseEntity.ok(quizDTOs);
    }

    private QuizDTO convertToQuizDTO(Quiz quiz) {
        if (quiz == null) {
            return null;
        }

        QuizDTO dto = new QuizDTO();
        dto.setQuizId((long) quiz.getQuizId());
        dto.setQuizName(quiz.getQuizName());
        dto.setQuizDescription(quiz.getQuizDescription());
        dto.setDifficultyLevel(quiz.getDifficultyLevel());
        dto.setMultimedia(quiz.getMultimedia());

        dto.setCategoryId(
                quiz.getCategory() != null ? (long) quiz.getCategory().getCategoryId() : null);

        dto.setCreatorId(quiz.getCreator() != null ? (long) quiz.getCreator().getUserId() : null);

        return dto;
    }

    @GetMapping("/allquizzes")
    public ResponseEntity<List<QuizDTO>> getAllQuizzes() {
        List<Quiz> quizzes = quizService.getAllQuizzes();
        List<QuizDTO> quizDTOs =
                quizzes.stream().map(this::convertToQuizDTO).collect(Collectors.toList());
        return ResponseEntity.ok(quizDTOs);
    }
}
