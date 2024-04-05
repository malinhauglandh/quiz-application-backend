package org.ntnu.idi.idatt2105.project.exception;

/**
 * Exception thrown when a user is not found.
 */
public class UserNotFoundException extends RuntimeException{

        /**
        * Constructs a new UserNotFoundException with the specified detail message.
        * @param message the detail message.
        */
        public UserNotFoundException(String message) {
            super(message);
        }
}
