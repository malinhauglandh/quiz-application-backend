package org.ntnu.idi.idatt2105.project.exception;

/** Exception thrown when a user already exists in the database. */
public class ExistingUserException extends RuntimeException {

    /**
     * Constructs a new ExistingUserException with the specified detail message.
     *
     * @param message the detail message.
     */
    public ExistingUserException(String message) {
        super(message);
    }
}
