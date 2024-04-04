package org.ntnu.idi.idatt2105.project.controller;

import java.util.List;
import java.util.Optional;
import org.ntnu.idi.idatt2105.project.entity.QuestionType;
import org.ntnu.idi.idatt2105.project.service.QuestionTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("api/tags")
public class QuestionTypeController {

    private final QuestionTypeService questionTypeService;

    @Autowired
    public QuestionTypeController(QuestionTypeService questionTypeService) {
        this.questionTypeService = questionTypeService;
    }

    @PostMapping
    public ResponseEntity<QuestionType> createQuestionType(@RequestBody String type) {
        QuestionType questionType = questionTypeService.createQuestionType(type);
        return new ResponseEntity<>(questionType, HttpStatus.CREATED);
    }

    /*@GetMapping("/{id}")
    public ResponseEntity<QuestionType> getQuestionTypeById(@PathVariable Long id) {
        Optional<QuestionType> questiontype = questionTypeService.findTypeByID(id);
        return questiontype
                .map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }*/

    @GetMapping
    public ResponseEntity<List<QuestionType>> getAllQuestionTypes() {
        List<QuestionType> questionTypes = questionTypeService.findAllTypes();
        return new ResponseEntity<>(questionTypes, HttpStatus.OK);
    }
}
