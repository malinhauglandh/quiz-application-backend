package org.ntnu.idi.idatt2105.project.controller.question;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.ntnu.idi.idatt2105.project.dto.question.QuestionChoiceDTO;
import org.ntnu.idi.idatt2105.project.dto.question.QuestionDTO;
import org.ntnu.idi.idatt2105.project.entity.question.Question;
import org.ntnu.idi.idatt2105.project.entity.question.QuestionChoice;
import org.ntnu.idi.idatt2105.project.mapper.question.QuestionChoiceMapper;
import org.ntnu.idi.idatt2105.project.service.question.QuestionService;
import org.ntnu.idi.idatt2105.project.service.question.QuestionTypeService;
import org.ntnu.idi.idatt2105.project.service.quiz.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/** Controller for handling HTTP requests for questions. */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/questions")
@Tag(name = "Question Management", description = "Endpoints for managing questions")
public class QuestionController {

    /** The service class for the question controller. */
    private final QuestionService questionService;

    /** The service class for the quiz controller. */
    private final QuizService quizService;

    /** The service class for the question type controller. */
    private final QuestionTypeService questionTypeService;

    /** The mapper class for the question choice controller. */
    private final QuestionChoiceMapper questionChoiceMapper;

    /**
     * Creates a new question controller with the specified services.
     *
     * @param questionService questionService
     * @param quizService quizService
     * @param questionTypeService questionTypeService
     * @param questionChoiceMapper questionChoiceMapper
     */
    @Autowired
    public QuestionController(
            QuestionService questionService,
            QuizService quizService,
            QuestionTypeService questionTypeService,
            QuestionChoiceMapper questionChoiceMapper) {
        this.questionService = questionService;
        this.quizService = quizService;
        this.questionTypeService = questionTypeService;
        this.questionChoiceMapper = questionChoiceMapper;
    }

    /**
     * Creates a new question with the specified question text, tag, quiz id, question type id,
     * choices and file.
     *
     * @param questionText questionText
     * @param tag tag
     * @param quizId quizId
     * @param questionTypeId questionTypeId
     * @param choices choices
     * @param file file
     * @return questionDTO
     */
    @Operation(
            summary = "Create a new question",
            responses = {
                @ApiResponse(responseCode = "200", description = "Question created"),
                @ApiResponse(responseCode = "400", description = "Invalid input")
            })
    @PostMapping("/create")
    public ResponseEntity<QuestionDTO> createQuestion(
            @RequestParam("questionText") String questionText,
            @RequestParam("tag") String tag,
            @RequestParam("quizId") Long quizId,
            @RequestParam("questionTypeId") Long questionTypeId,
            @RequestParam("choices") String choices,
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
        question.getQuestionChoiceList().clear();
        List<QuestionChoice> mappedChoices =
                choiceDTOs.stream()
                        .map(
                                   dto ->
                                        questionChoiceMapper.questionChoiceDTOToQuestionChoice(
                                                dto, question))
                        .toList();
        question.getQuestionChoiceList().addAll(mappedChoices);
        QuestionDTO createdQuestionDTO = questionService.createQuestion(question);
        return ResponseEntity.ok(createdQuestionDTO);
    }

    /**
     * Get all questions.
     *
     * @return list of questions
     */
    @Operation(
            summary = "Get all questions",
            responses = {
                @ApiResponse(responseCode = "200", description = "Questions found"),
                @ApiResponse(responseCode = "404", description = "Questions not found")
            })
    @GetMapping("/allQuestions")
    public ResponseEntity<List<QuestionDTO>> getAllQuestions() {
        List<QuestionDTO> questions = questionService.getAllQuestions();
        return ResponseEntity.ok(questions);
    }
}
