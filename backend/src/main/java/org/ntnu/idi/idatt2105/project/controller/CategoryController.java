package org.ntnu.idi.idatt2105.project.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Category Management", description = "Endpoints for managing categories")
public class CategoryController {

    private final CategoryService categoryService;
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryController(
            CategoryService categoryService, CategoryRepository categoryRepository) {
        this.categoryService = categoryService;
        this.categoryRepository = categoryRepository;
    }

    @Operation(summary = "Create a new category",
        parameters = {
            @Parameter(name = "categoryName",
                description = "The name of the category")
        },
        responses = {
            @ApiResponse(responseCode = "201", description = "Category created"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
        })
    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestBody String categoryName) {
        Category category = categoryService.createCategory(categoryName);
        return new ResponseEntity<>(category, HttpStatus.CREATED);
    }

    @Operation(summary = "Get a category by id",
        parameters = {
            @Parameter(name = "id",
                description = "Category id")
        },
        responses = {
            @ApiResponse(responseCode = "200", description = "Category found"),
            @ApiResponse(responseCode = "404", description = "Category not found")
        })
    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategory(@PathVariable Long id) {
        Optional<Category> category = categoryService.findCategoryById(id);
        return category.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Operation(summary = "Get all categories",
        responses = {
            @ApiResponse(responseCode = "200", description = "Categories found"),
            @ApiResponse(responseCode = "404", description = "Categories not found")
        })
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
