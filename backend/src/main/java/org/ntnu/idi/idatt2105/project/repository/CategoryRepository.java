package org.ntnu.idi.idatt2105.project.repository;

import java.util.Optional;
import org.ntnu.idi.idatt2105.project.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

/** Repository interface for the Category entity. */
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findById(Long categoryId);
}
