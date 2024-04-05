package org.ntnu.idi.idatt2105.project.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.ntnu.idi.idatt2105.project.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class CategoryRepositoryTest {

  @Autowired
  private CategoryRepository categoryRepository;

  private Category savedCategory;

  @BeforeEach
  void setUp() {
    Category category = new Category();
    category.setCategoryName("Test Category");
    savedCategory = categoryRepository.save(category);
  }

  @Test
  void verifyFindByIdFindsExistingCategory() {
    Optional<Category> found = categoryRepository.findById((long) savedCategory.getCategoryId());
    assertThat(found).isPresent().contains(savedCategory);
  }

  @Test
  void verifyFindByIdWithInvalidIdReturnsEmpty() {
    int nonExistentId = Integer.MAX_VALUE;
    Optional<Category> foundCategory = categoryRepository.findById((long) nonExistentId);
    assertThat(foundCategory).isNotPresent();
  }

  @Test
  void verifySaveUpdatesCategoryNameProperly() {
    savedCategory.setCategoryName("Updated Category");
    categoryRepository.save(savedCategory);

    Optional<Category> updatedCategory = categoryRepository.findById((long) savedCategory.getCategoryId());
    assertThat(updatedCategory).isPresent();
    assertThat(updatedCategory.get().getCategoryName()).isEqualTo("Updated Category");
  }

  @Test
  void verifyDeletingCategoryRemovesItFromDatabase() {
    long categoryId = savedCategory.getCategoryId();
    categoryRepository.delete(savedCategory);

    Optional<Category> foundCategory = categoryRepository.findById(categoryId);
    assertThat(foundCategory).isNotPresent();
  }
}
