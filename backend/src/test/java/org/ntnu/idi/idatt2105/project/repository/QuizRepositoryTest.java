package org.ntnu.idi.idatt2105.project.repository;

import org.junit.jupiter.api.Test;
import org.ntnu.idi.idatt2105.project.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.ntnu.idi.idatt2105.project.entity.Quiz;
import org.ntnu.idi.idatt2105.project.entity.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class QuizRepositoryTest {

  @Autowired
  private QuizRepository quizRepository;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private CategoryRepository categoryRepository;

  @Test
  @Transactional
  public void verifyFindByCreator_UserIdReturnsQuizForUser() {
    Category category = new Category();
    category.setCategoryName("Test Category");
    category = categoryRepository.save(category);

    User user = new User();
    user.setUsername("User");
    user.setEmail("user@test.com");
    user.setPassword("password");
    user = userRepository.save(user);

    Quiz quiz = new Quiz();
    quiz.setQuizName("Example Quiz");
    quiz.setDifficultyLevel("Easy");
    quiz.setCategory(category);
    quiz.setCreator(user);
    quiz = quizRepository.save(quiz);

    List<Quiz> foundQuizzes = quizRepository.findByCreator_UserId((long) user.getUserId());
    assertThat(foundQuizzes).contains(quiz);
  }

  @Test
  @Transactional
  public void verifyFindByCreator_UserIdReturnsEmptyForUserWithoutQuizzes() {
    User user = new User();
    user.setUsername("User");
    user.setEmail("user@test.com");
    user.setPassword("password");
    user = userRepository.save(user);

    List<Quiz> foundQuizzes = quizRepository.findByCreator_UserId((long) user.getUserId());
    assertThat(foundQuizzes).isEmpty();
  }

  @Test
  @Transactional
  public void verifyAllUserQuizzesAreReturnedForMultipleQuizzes() {
    User user = new User();
    user.setUsername("User");
    user.setEmail("user@test.com");
    user.setPassword("password");
    user = userRepository.save(user);

    Category category = new Category();
    category.setCategoryName("Test Category");
    category = categoryRepository.save(category);

    Quiz firstQuiz = new Quiz();
    firstQuiz.setQuizName("First Quiz");
    firstQuiz.setDifficultyLevel("Medium");
    firstQuiz.setCategory(category);
    firstQuiz.setCreator(user);
    quizRepository.save(firstQuiz);

    Quiz secondQuiz = new Quiz();
    secondQuiz.setQuizName("Second Quiz");
    secondQuiz.setDifficultyLevel("Hard");
    secondQuiz.setCategory(category);
    secondQuiz.setCreator(user);
    quizRepository.save(secondQuiz);

    List<Quiz> foundQuizzes = quizRepository.findByCreator_UserId((long) user.getUserId());
    assertThat(foundQuizzes).hasSize(2).contains(firstQuiz, secondQuiz);
  }

  @Test
  @Transactional
  public void verifyOnlyQuizzesByCorrectCreatorUserIdAreReturned() {
    User userOne = new User();
    userOne.setUsername("UserOne");
    userOne.setEmail("userOne@test.com");
    userOne.setPassword("password");
    userOne = userRepository.save(userOne);

    User userTwo = new User();
    userTwo.setUsername("UserTwo");
    userTwo.setEmail("userTwo@test.com");
    userTwo.setPassword("password");
    userTwo = userRepository.save(userTwo);

    Category sharedCategory = new Category();
    sharedCategory.setCategoryName("Test Category");
    sharedCategory = categoryRepository.save(sharedCategory);

    Quiz userOneQuiz = new Quiz();
    userOneQuiz.setQuizName("Quiz User One");
    userOneQuiz.setDifficultyLevel("Easy");
    userOneQuiz.setCategory(sharedCategory);
    userOneQuiz.setCreator(userOne);
    quizRepository.save(userOneQuiz);

    Quiz userTwoQuiz = new Quiz();
    userTwoQuiz.setQuizName("Quiz User Two");
    userTwoQuiz.setDifficultyLevel("Hard");
    userTwoQuiz.setCategory(sharedCategory);
    userTwoQuiz.setCreator(userTwo);
    quizRepository.save(userTwoQuiz);

    List<Quiz> quizzesForUserOne = quizRepository.findByCreator_UserId((long) userOne.getUserId());
    assertThat(quizzesForUserOne).containsOnly(userOneQuiz);

    List<Quiz> quizzesForUserTwo = quizRepository.findByCreator_UserId((long) userTwo.getUserId());
    assertThat(quizzesForUserTwo).containsOnly(userTwoQuiz);
  }

}
