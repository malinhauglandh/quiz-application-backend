package org.ntnu.idi.idatt2105.project.controller;

import java.util.List;
import java.util.Optional;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.ntnu.idi.idatt2105.project.entity.QuestionType;
import org.ntnu.idi.idatt2105.project.service.QuestionTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("api/tags")
@Tag(name = "Question Type Management", description = "Endpoints for managing question types")
public class QuestionTypeController {

    private final QuestionTypeService questionTypeService;

    @Autowired
    public QuestionTypeController(QuestionTypeService questionTypeService) {
        this.questionTypeService = questionTypeService;
    }

    @Operation(summary = "Create a new question type",
        parameters = {
            @Parameter(name = "type",
                description = "The type of the question")
        },
        responses = {
            @ApiResponse(responseCode = "201", description = "Question type created"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping("/createquestiontype")
    public ResponseEntity<QuestionType> createQuestionType(@RequestBody String type) {
        QuestionType questionType = questionTypeService.createQuestionType(type);
        return new ResponseEntity<>(questionType, HttpStatus.CREATED);
    }

    @Operation(summary = "Get a question type by id",
        parameters = {
            @Parameter(name = "id",
                description = "Question type id")
        },
        responses = {
            @ApiResponse(responseCode = "200", description = "Question type found"),
            @ApiResponse(responseCode = "404", description = "Question type not found")
        })
    @GetMapping("/{id}")
    public ResponseEntity<QuestionType> getQuestionTypeById(@PathVariable Long id) {
        Optional<QuestionType> questiontype = questionTypeService.findTypeByID(id);
        return questiontype
                .map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Operation(summary = "Get all question types",
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
