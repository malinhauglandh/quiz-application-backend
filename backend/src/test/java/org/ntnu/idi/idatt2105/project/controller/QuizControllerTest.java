package org.ntnu.idi.idatt2105.project.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.ntnu.idi.idatt2105.project.controller.quiz.QuizController;
import org.ntnu.idi.idatt2105.project.dto.quiz.CreateQuizDTO;
import org.ntnu.idi.idatt2105.project.mapper.quiz.QuizMapper;
import org.ntnu.idi.idatt2105.project.service.FileStorageService;
import org.ntnu.idi.idatt2105.project.service.quiz.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(QuizController.class)
class QuizControllerTest {

    @Autowired private MockMvc mockMvc;

    @MockBean private QuizService quizService;

    @MockBean private FileStorageService fileStorageService;

    @MockBean private QuizMapper quizMapper;

    @BeforeEach
    public void setup(WebApplicationContext webApplicationContext) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void createQuiz() throws Exception {
        CreateQuizDTO quizDTO = new CreateQuizDTO();
        quizDTO.setQuizName("Test Quiz");
        quizDTO.setQuizDescription("Test Description");
        quizDTO.setDifficultyLevel("Easy");
        quizDTO.setCategoryId(1L);
        quizDTO.setCreatorId(1L);

        given(quizService.createQuiz(any(CreateQuizDTO.class))).willReturn(quizDTO);

        MockMultipartFile file =
                new MockMultipartFile(
                        "file", "filename.txt", "text/plain", "testContent".getBytes());

        mockMvc.perform(
                        multipart("/api/quizzes/createQuiz")
                                .file(file)
                                .param("quizName", quizDTO.getQuizName())
                                .param("quizDescription", quizDTO.getQuizDescription())
                                .param("difficultyLevel", quizDTO.getDifficultyLevel())
                                .param("category", quizDTO.getCategoryId().toString())
                                .param("creator", quizDTO.getCreatorId().toString())
                                .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isOk());
    }
}
