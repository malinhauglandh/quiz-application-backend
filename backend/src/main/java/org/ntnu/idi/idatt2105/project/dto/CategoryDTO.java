package org.ntnu.idi.idatt2105.project.dto;

import lombok.*;

/** Data transfer object representing a category in the application. */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO {
    /** The category id, autogenerated by the database. */
    private Long categoryId;

    /** The name of the category. */
    private String categoryName;
}
