package org.ntnu.idi.idatt2105.project.exception;

/** Exception thrown when a category is not found. */
public class CategoryNotFoundException extends RuntimeException {

    /**
     * Constructs a new CategoryNotFoundException with the specified detail message.
     *
     * @param message the detail message.
     */
    public CategoryNotFoundException(String message) {
        super(message);
    }
}
