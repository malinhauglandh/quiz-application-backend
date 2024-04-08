package org.ntnu.idi.idatt2105.project.exception;

/**
 * Exception thrown when a quiz is not found.

 */
public class QuizNotFoundException extends RuntimeException{

    /**
     * Constructs and initializes the exception using the specified message.
     *
     * @param message the message to be displayed when the exception is thrown
     */
    public QuizNotFoundException(String message) {
        super(message);
    }
}
