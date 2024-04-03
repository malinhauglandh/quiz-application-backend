package org.ntnu.idi.idatt2105.project.service;

import java.util.List;
import java.util.Optional;

import org.ntnu.idi.idatt2105.project.entity.QuestionType;
import org.ntnu.idi.idatt2105.project.repository.QuestionTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuestionTypeService {

    private final QuestionTypeRepository questionTypeRepository;

    @Autowired
    public QuestionTypeService(QuestionTypeRepository questionTypeRepository) {
        this.questionTypeRepository = questionTypeRepository;
    }

    public QuestionType createQuestionType(String type) {
        QuestionType questionType = new QuestionType();
        questionType.setType(type);
        return questionTypeRepository.save(questionType);
    }

    public Optional<QuestionType> findTypeByID(Long id) {
        return questionTypeRepository.findById(id);
    }

    public List<QuestionType> findAllTypes() {
        return questionTypeRepository.findAll();
    }
}
