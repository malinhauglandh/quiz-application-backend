package org.ntnu.idi.idatt2105.project.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.stream.Collectors;
import org.ntnu.idi.idatt2105.project.dto.QuizDTO;
import org.ntnu.idi.idatt2105.project.entity.Quiz;
import org.ntnu.idi.idatt2105.project.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * Controller for handling HTTP requests for quizzes.
 */
@RestController
@RequestMapping("api/quizzes")
@CrossOrigin(origins = "*")
@Tag(name = "Quiz Management", description = "Endpoints for managing quizzes and multimedia files")
public class QuizController {

    /**
     * The service class for the quiz controller.
     */
    private final QuizService quizService;

    /**
     * The service class for the file storage controller.
     */
    private final FileStorageService fileStorageService;

    /**
     * The mapper class for the quiz controller.
     */
    private final QuizMapper quizMapper;

    /**
     * Creates a new quiz controller with the specified services.
     * @param quizService quizService
     * @param fileStorageService fileStorageService
     * @param quizMapper quizMapper
     */
    @Autowired
    public QuizController(
            QuizService quizService,
            FileStorageService fileStorageService,
            QuizMapper quizMapper) {
        this.quizService = quizService;
        this.fileStorageService = fileStorageService;
        this.quizMapper = quizMapper;
    }

    /**
     * Create a new quiz with the specified quiz name, quiz description, difficulty level, category id, creator id and file.
     * @param quizName name of the quiz
     * @param quizDescription description of the quiz
     * @param difficultyLevel difficulty level of the quiz
     * @param categoryId id of the category
     * @param creatorId id of the creator
     * @param file file
     * @return quizDTO
     */
    @Operation(
            summary = "Create a new quiz",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Quiz created"),
                    @ApiResponse(responseCode = "400", description = "Not able to create quiz")
            })
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

    /**
     * Upload a multimedia-file to a quiz.
     * @param file file
     * @param quizId id of the quiz
     * @return response
     */
    @Operation(
            summary = "Upload a multimedia-file to a quiz",
            parameters = {
                    @Parameter(name = "file", description = "The multimedia-file to upload"),
                    @Parameter(
                            name = "quizId",
                            description = "The id of the quiz to link the multimedia-file to")
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Multimedia-file uploaded successfully"),
                    @ApiResponse(responseCode = "404", description = "Multimedia-file not uploaded")
            })
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

    /**
     * Get a multimedia-file by filename.
     * @param fileName name of the multimedia-file
     * @return multimedia-file
     */
    @Operation(
            summary = "Get a multimedia-file by filename",
            parameters = {
                    @Parameter(name = "fileName", description = "The name of the multimedia-file")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Multimedia-file found"),
                    @ApiResponse(responseCode = "404", description = "Multimedia-file not found")
            })
    @GetMapping("/files/{fileName:.+}")
    public ResponseEntity<byte[]> getFile(@PathVariable String fileName) {
        return fileStorageService.loadFile(fileName);
    }

    /**
     * Get a quiz by creator id.
     * @param creatorId id of the creator
     * @return quizDTO
     */
    @Operation(
            summary = "Get a quiz by creatorId",
            parameters = {@Parameter(name = "creatorId", description = "The id of the creator")},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Quiz by creator found"),
                    @ApiResponse(responseCode = "404", description = "Quiz by creator not found")
            })
    @GetMapping("/user/{creatorId}")
    public ResponseEntity<List<QuizDTO>> getQuizzesByCreator(@PathVariable Long creatorId) {
        List<Quiz> quizzes = quizService.getQuizzesByCreatorId(creatorId);
        List<QuizDTO> quizDTOs =
                quizzes.stream().map(quizMapper::toDto).collect(Collectors.toList());
        return ResponseEntity.ok(quizDTOs);
    }


    /**
     * Get all quizzes.
     * @return list of quizzes
     */
    @Operation(
            summary = "Get all quizzes",
            responses = {
                    @ApiResponse(responseCode = "200", description = "All quizzes are found"),
                    @ApiResponse(responseCode = "404", description = "Quizzes not found")
            })
    @GetMapping("/allQuizzes")
    public ResponseEntity<List<QuizDTO>> getAllQuizzes() {
        List<QuizDTO> quizzes = quizService.getAllQuizzes();
        return ResponseEntity.ok(quizzes);
    }
}