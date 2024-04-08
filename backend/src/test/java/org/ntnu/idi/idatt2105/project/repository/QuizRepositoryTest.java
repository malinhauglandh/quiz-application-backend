package org.ntnu.idi.idatt2105.project.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.ntnu.idi.idatt2105.project.entity.Category;
import org.ntnu.idi.idatt2105.project.entity.quiz.Quiz;
import org.ntnu.idi.idatt2105.project.entity.user.User;
import org.ntnu.idi.idatt2105.project.repository.quiz.QuizRepository;
import org.ntnu.idi.idatt2105.project.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@DataJpaTest
public class QuizRepositoryTest {

    @Autowired private QuizRepository quizRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private CategoryRepository categoryRepository;

    private User savedUser;

    @BeforeEach
    void setUp() {
        User user = new User();
        user.setUsername("testUser");
        user.setPassword("password");
        user.setEmail("user@test.com");
        this.savedUser = userRepository.save(user);

        Category category = new Category();
        category.setCategoryName("Test Category");
        Category savedCategory = categoryRepository.save(category);

        Quiz firstQuiz = new Quiz();
        firstQuiz.setQuizName("Test Quiz 1");
        firstQuiz.setQuizDescription("Test Quiz 1 Description");
        firstQuiz.setDifficultyLevel("Easy");
        firstQuiz.setCreator(savedUser);
        firstQuiz.setCategory(savedCategory);
        quizRepository.save(firstQuiz);

        Quiz secondQuiz = new Quiz();
        secondQuiz.setQuizName("Test Quiz 2");
        secondQuiz.setQuizDescription("Test Quiz 2 Description");
        secondQuiz.setDifficultyLevel("Medium");
        secondQuiz.setCreator(savedUser);
        secondQuiz.setCategory(savedCategory);
        quizRepository.save(secondQuiz);
    }

    @Test
    void verifyFindByCreatorIdReturnQuizzes() {
        List<Quiz> quizzes = quizRepository.findByCreator_UserId(savedUser.getUserId());
        assertThat(quizzes).isNotEmpty();
        assertThat(quizzes).allMatch(q -> q.getCreator().getUserId().equals(savedUser.getUserId()));
    }

    @Test
    void verifyFindByCreatorIdAndQuizIdReturnSpecificQuiz() {
        Long creatorId = savedUser.getUserId();

        List<Quiz> createdQuizzes = quizRepository.findByCreator_UserId(creatorId);
        assertThat(createdQuizzes).isNotEmpty();
        Long quizId = createdQuizzes.get(0).getQuizId();

        List<Quiz> foundQuizzes = quizRepository.findByCreator_UserIdAndQuizId(creatorId, quizId);

        assertThat(foundQuizzes)
                .isNotEmpty()
                .allMatch(
                        quiz ->
                                quiz.getQuizId().equals(quizId)
                                        && quiz.getCreator().getUserId().equals(creatorId));
    }

    @Test
    void verifyFindAllReturnAllQuizzes() {
        List<Quiz> allQuizzes = quizRepository.findAll();

        assertThat(allQuizzes).isNotEmpty();
        assertThat(allQuizzes).hasSize(2);
    }
}
