package org.ntnu.idi.idatt2105.project.controller;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.ntnu.idi.idatt2105.project.controller.question.QuestionTypeController;
import org.ntnu.idi.idatt2105.project.entity.question.QuestionType;
import org.ntnu.idi.idatt2105.project.service.question.QuestionTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.List;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
@WebMvcTest(QuestionTypeController.class)
class QuestionTypeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private QuestionTypeService questionTypeService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    void setup() {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();
    }
    @Test
    @WithMockUser
    void getAllQuestionTypes() throws Exception {
        QuestionType questionTypeOne = new QuestionType();
        questionTypeOne.setTypeId(1L);
        questionTypeOne.setType("Question type 1");

        QuestionType questionTypeTwo = new QuestionType();
        questionTypeTwo.setTypeId(2L);
        questionTypeTwo.setType("Question type 2");

        List<QuestionType> allQuestionTypes = Arrays.asList(questionTypeOne, questionTypeTwo);

        given(questionTypeService.findAllTypes()).willReturn(allQuestionTypes);

        mockMvc.perform(get("/api/tags")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(allQuestionTypes)));
    }
}

