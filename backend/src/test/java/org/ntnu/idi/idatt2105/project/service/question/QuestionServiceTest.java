package org.ntnu.idi.idatt2105.project.service.question;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.ntnu.idi.idatt2105.project.dto.question.QuestionDTO;
import org.ntnu.idi.idatt2105.project.entity.question.Question;
import org.ntnu.idi.idatt2105.project.mapper.question.QuestionMapper;
import org.ntnu.idi.idatt2105.project.repository.question.QuestionRepository;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.server.ResponseStatusException;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class QuestionServiceTest {

    @Mock private QuestionRepository questionRepository;

    @Mock private QuestionMapper questionMapper;

    @Mock private ObjectMapper objectMapper;

    @InjectMocks private QuestionService questionService;

    @BeforeEach
    void setUp() {
        // Initialization code, if necessary
    }

    @Test
    void verifyCreateQuestion() {
        Question question = new Question();
        Question savedQuestion = new Question();
        QuestionDTO questionDTO = new QuestionDTO();

        when(questionRepository.save(any(Question.class))).thenReturn(savedQuestion);
        when(questionMapper.questionToQuestionDTO(any(Question.class))).thenReturn(questionDTO);

        QuestionDTO result = questionService.createQuestion(question);

        assertNotNull(result);
        verify(questionRepository).save(question);
        verify(questionMapper).questionToQuestionDTO(savedQuestion);
    }

    @Test
    void verifyGetAllQuestionsReturnsAllQuestions() {
        List<Question> questions = new ArrayList<>();
        questions.add(new Question());
        List<QuestionDTO> questionDTOs = new ArrayList<>();
        questionDTOs.add(new QuestionDTO());

        when(questionRepository.findAll()).thenReturn(questions);
        when(questionMapper.questionToQuestionDTO(any(Question.class)))
                .thenReturn(new QuestionDTO());

        List<QuestionDTO> results = questionService.getAllQuestions();

        assertNotNull(results);
        assertEquals(1, results.size());
        verify(questionRepository).findAll();
        verify(questionMapper, times(questions.size())).questionToQuestionDTO(any(Question.class));
    }

    @Test
    void testParseChoicesWithParsingFailure() throws JsonProcessingException {
        String invalidJson = "invalid json";
        doThrow(JsonProcessingException.class)
                .when(objectMapper)
                .readValue(anyString(), any(TypeReference.class));

        assertThrows(
                ResponseStatusException.class, () -> questionService.parseChoices(invalidJson));
    }
}
