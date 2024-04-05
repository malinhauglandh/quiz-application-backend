package org.ntnu.idi.idatt2105.project.service;

import java.util.List;

import org.ntnu.idi.idatt2105.project.entity.QuestionType;
import org.ntnu.idi.idatt2105.project.repository.QuestionTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service class for QuestionType
 */
@Service
public class QuestionTypeService {

    /**
     * Repository for QuestionType
     */
    private final QuestionTypeRepository questionTypeRepository;

    /**
     * Constructor for QuestionTypeService
     * @param questionTypeRepository The repository for QuestionType
     */
    @Autowired
    public QuestionTypeService(QuestionTypeRepository questionTypeRepository) {
        this.questionTypeRepository = questionTypeRepository;
    }

    /**
     * Create a new QuestionType
     * @param type The type of the question
     * @return The created QuestionType
     */
    public QuestionType createQuestionType(String type) {
        QuestionType questionType = new QuestionType();
        questionType.setType(type);
        return questionTypeRepository.save(questionType);
    }

    /**
     * Find all QuestionTypes
     * @return A list of all QuestionTypes
     */
    public List<QuestionType> findAllTypes() {
        return questionTypeRepository.findAll();
    }

    /**
     * Find a QuestionType by ID
     * @param typeId The ID of the QuestionType
     * @return The QuestionType
     */
    public QuestionType findTypeByID(Long typeId) {
        return questionTypeRepository.findById(typeId).orElse(null);
    }
}
