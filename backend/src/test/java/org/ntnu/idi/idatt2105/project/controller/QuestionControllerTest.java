package org.ntnu.idi.idatt2105.project.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.ntnu.idi.idatt2105.project.controller.question.QuestionController;
import org.ntnu.idi.idatt2105.project.dto.question.QuestionDTO;
import org.ntnu.idi.idatt2105.project.mapper.question.QuestionChoiceMapper;
import org.ntnu.idi.idatt2105.project.service.question.QuestionService;
import org.ntnu.idi.idatt2105.project.service.question.QuestionTypeService;
import org.ntnu.idi.idatt2105.project.service.quiz.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = QuestionController.class)
public class QuestionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private QuestionService questionService;
    @MockBean
    private QuizService quizService;
    @MockBean
    private QuestionTypeService questionTypeService;
    @MockBean
    private QuestionChoiceMapper questionChoiceMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup(WebApplicationContext webApplicationContext) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void verifyCreateQuestion() throws Exception {
        QuestionDTO questionDTO = new QuestionDTO();
        questionDTO.setQuestionText("Test Question");
        questionDTO.setTag("Test Tag");

        given(questionService.createQuestion(any())).willReturn(questionDTO);

        MockMultipartFile file = new MockMultipartFile("file", "filename.txt", "text/plain", "test".getBytes());

        mockMvc.perform(multipart("/api/questions/create")
                        .file(file)
                        .param("questionText", questionDTO.getQuestionText())
                        .param("tag", questionDTO.getTag())
                        .param("quizId", "1")
                        .param("questionTypeId", "1")
                        .param("choices", "choice1,choice2")
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void verifyGetAllQuestionsReturnAllQuestions() throws Exception {
        List<QuestionDTO> questions = Arrays.asList(new QuestionDTO(), new QuestionDTO());
        given(questionService.getAllQuestions()).willReturn(questions);

        mockMvc.perform(get("/api/questions/allQuestions"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(questions)));
    }
}
