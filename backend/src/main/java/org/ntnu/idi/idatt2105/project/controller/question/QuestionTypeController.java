package org.ntnu.idi.idatt2105.project.controller.question;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.ntnu.idi.idatt2105.project.entity.question.QuestionType;
import org.ntnu.idi.idatt2105.project.service.question.QuestionTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/** Controller for handling HTTP requests for question types. */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("api/tags")
@Tag(name = "Question Type Management", description = "Endpoints for managing question types")
public class QuestionTypeController {

    /** The service class for the question type controller. */
    private final QuestionTypeService questionTypeService;

    /**
     * Creates a new question type controller with the specified service.
     *
     * @param questionTypeService questionTypeService
     */
    @Autowired
    public QuestionTypeController(QuestionTypeService questionTypeService) {
        this.questionTypeService = questionTypeService;
    }

    /**
     * Endpoint for getting all question types.
     *
     * @return ResponseEntity with status 200 and a list of question types if found, or status 404 if
     *     no question types were found
     */
    @Operation(
            summary = "Get all question types",
            responses = {
                @ApiResponse(responseCode = "200", description = "Question types found"),
                @ApiResponse(responseCode = "404", description = "Question types not found")
            })
    @GetMapping
    public ResponseEntity<List<QuestionType>> getAllQuestionTypes() {
        List<QuestionType> questionTypes = questionTypeService.findAllTypes();
        return new ResponseEntity<>(questionTypes, HttpStatus.OK);
    }
}
