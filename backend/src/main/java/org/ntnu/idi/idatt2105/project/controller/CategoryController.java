package org.ntnu.idi.idatt2105.project.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.ntnu.idi.idatt2105.project.dto.CategoryDTO;
import org.ntnu.idi.idatt2105.project.entity.Category;
import org.ntnu.idi.idatt2105.project.repository.CategoryRepository;
import org.ntnu.idi.idatt2105.project.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("api/categories")
public class CategoryController {

    private final CategoryService categoryService;
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryController(
            CategoryService categoryService, CategoryRepository categoryRepository) {
        this.categoryService = categoryService;
        this.categoryRepository = categoryRepository;
    }

    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestBody String categoryName) {
        Category category = categoryService.createCategory(categoryName);
        return new ResponseEntity<>(category, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategory(@PathVariable Long id) {
        Optional<Category> category = categoryService.findCategoryById(id);
        return category.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/allCategories")
    public List<CategoryDTO> findAllCategories() {
        return categoryRepository.findAll().stream()
                .map(
                        category ->
                                new CategoryDTO(
                                        category.getCategoryId(), category.getCategoryName()))
                .collect(Collectors.toList());
    }
}
