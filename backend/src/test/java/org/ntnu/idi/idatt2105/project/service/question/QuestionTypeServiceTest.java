package org.ntnu.idi.idatt2105.project.service.question;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.ntnu.idi.idatt2105.project.entity.question.QuestionType;
import org.ntnu.idi.idatt2105.project.repository.question.QuestionTypeRepository;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class QuestionTypeServiceTest {

    @Mock private QuestionTypeRepository questionTypeRepository;

    @InjectMocks private QuestionTypeService questionTypeService;

    @Test
    void verifyCreateQuestionType() {
        String type = "Test question type";
        QuestionType questionType = new QuestionType();
        questionType.setType(type);

        when(questionTypeRepository.save(any(QuestionType.class))).thenReturn(questionType);

        QuestionType createdType = questionTypeService.createQuestionType(type);

        assertEquals(type, createdType.getType());
        verify(questionTypeRepository).save(any(QuestionType.class));
    }

    @Test
    void verifyFindAllTypesReturnAllTypes() {
        List<QuestionType> types = Arrays.asList(new QuestionType(), new QuestionType());

        when(questionTypeRepository.findAll()).thenReturn(types);

        List<QuestionType> foundTypes = questionTypeService.findAllTypes();

        assertEquals(2, foundTypes.size());
        verify(questionTypeRepository).findAll();
    }

    @Test
    void verifyFindTypeByIdReturnsTypeWithCorrectId() {
        Long typeId = 1L;
        QuestionType questionType = new QuestionType();
        questionType.setTypeId(typeId);

        when(questionTypeRepository.findById(typeId)).thenReturn(Optional.of(questionType));

        QuestionType foundType = questionTypeService.findTypeByID(typeId);

        assertEquals(typeId, foundType.getTypeId());
        verify(questionTypeRepository).findById(typeId);
    }

    @Test
    void verifyFindTypeByIDWhenIdNotFound() {
        Long typeId = 2L;
        when(questionTypeRepository.findById(typeId)).thenReturn(Optional.empty());

        QuestionType foundType = questionTypeService.findTypeByID(typeId);

        assertNull(foundType);
        verify(questionTypeRepository).findById(typeId);
    }
}
