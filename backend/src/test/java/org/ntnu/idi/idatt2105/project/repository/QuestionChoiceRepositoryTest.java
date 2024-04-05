package org.ntnu.idi.idatt2105.project.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.ntnu.idi.idatt2105.project.entity.Category;
import org.ntnu.idi.idatt2105.project.entity.question.Question;
import org.ntnu.idi.idatt2105.project.entity.question.QuestionChoice;
import org.ntnu.idi.idatt2105.project.repository.user.UserRepository;
import org.ntnu.idi.idatt2105.project.entity.question.QuestionType;
import org.ntnu.idi.idatt2105.project.entity.quiz.Quiz;
import org.ntnu.idi.idatt2105.project.entity.user.User;
import org.ntnu.idi.idatt2105.project.repository.question.QuestionChoiceRepository;
import org.ntnu.idi.idatt2105.project.repository.question.QuestionRepository;
import org.ntnu.idi.idatt2105.project.repository.question.QuestionTypeRepository;
import org.ntnu.idi.idatt2105.project.repository.quiz.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@DataJpaTest
public class QuestionChoiceRepositoryTest {

    @Autowired
    private QuestionChoiceRepository questionChoiceRepository;
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private QuizRepository quizRepository;
    @Autowired
    private QuestionTypeRepository questionTypeRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private UserRepository userRepository;
    private Question savedQuestion;

    @BeforeEach
    void setUp() {
        User user = new User();
        user.setUsername("testUser");
        user.setPassword("password");
        user.setEmail("user@test.com");
        User savedCreator = userRepository.save(user);

        Category category = new Category();
        category.setCategoryName("Test Category");
        Category savedCategory = categoryRepository.save(category);

        Quiz quiz = new Quiz();
        quiz.setQuizName("Test Quiz");
        quiz.setCategory(savedCategory);
        quiz.setCreator(savedCreator);
        quiz.setDifficultyLevel("Easy");
        Quiz savedQuiz = quizRepository.save(quiz);

        QuestionType questionType = new QuestionType();
        questionType.setType("True or False");
        QuestionType savedQuestionType = questionTypeRepository.save(questionType);

        Question question = new Question();
        question.setQuestionText("Test Question Text");
        question.setTag("Test Tag");
        question.setQuiz(savedQuiz);
        question.setQuestionType(savedQuestionType);
        this.savedQuestion = questionRepository.save(question);

        QuestionChoice choiceTrue = new QuestionChoice();
        choiceTrue.setChoice("True");
        choiceTrue.setExplanation("Test explanation");
        choiceTrue.setCorrectChoice(true);
        choiceTrue.setQuestion(savedQuestion);
        questionChoiceRepository.save(choiceTrue);

        QuestionChoice choiceFalse = new QuestionChoice();
        choiceFalse.setChoice("False");
        choiceFalse.setExplanation("Test explanation.");
        choiceFalse.setCorrectChoice(false);
        choiceFalse.setQuestion(savedQuestion);
        questionChoiceRepository.save(choiceFalse);
    }

    @Test
    void verifyFindAllQuestionChoicesByQuestionId() {
        List<QuestionChoice> choices = questionChoiceRepository.findAllByQuestion(savedQuestion);
        assertThat(choices).isNotEmpty();
        assertThat(choices.size()).isEqualTo(2);
        assertThat(choices.get(0).getQuestion().getQuestionId()).isEqualTo(savedQuestion.getQuestionId());
    }

    @Test
    void verifySaveAndFindChoiceById() {
        QuestionChoice newChoice = new QuestionChoice();
        newChoice.setChoice("Test Choice");
        newChoice.setExplanation("Test explanation.");
        newChoice.setCorrectChoice(false);
        newChoice.setQuestion(savedQuestion);

        QuestionChoice savedChoice = questionChoiceRepository.save(newChoice);
        assertThat(savedChoice).isNotNull();
        assertThat(savedChoice.getQuizChoiceId()).isNotNull();
        assertThat(savedChoice.getChoice()).isEqualTo("Test Choice");

        Optional<QuestionChoice> foundChoice = questionChoiceRepository.findById(savedChoice.getQuizChoiceId());
        assertThat(foundChoice.isPresent()).isTrue();
        assertThat(foundChoice.get().getExplanation()).isEqualTo("Test explanation.");
    }

}

