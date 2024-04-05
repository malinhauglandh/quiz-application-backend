package org.ntnu.idi.idatt2105.project.service;

import java.util.Optional;
import org.ntnu.idi.idatt2105.project.entity.Category;
import org.ntnu.idi.idatt2105.project.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service class for Category
 */
@Service
public class CategoryService {

    /**
     * Repository for Category
     */
    private final CategoryRepository categoryRepository;

    /**
     * Constructor for CategoryService
     * @param categoryRepository The repository for Category
     */
    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    /**
     * Create a new Category
     * @param categoryName The name of the category
     * @return The created Category
     */
    public Category createCategory(String categoryName) {
        Category category = new Category();
        category.setCategoryName(categoryName);
        return categoryRepository.save(category);
    }

    /**
     * Find all Categories
     * @return A list of all Categories
     */
    public Optional<Category> findCategoryById(Long categoryId) {
        return categoryRepository.findById(categoryId);
    }
}
