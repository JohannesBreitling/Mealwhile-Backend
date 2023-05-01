package de.johannesbreitling.mealwhile.business.model.exceptions;

/**
 * Exception when a given Entity is not found in the repository
 */
public class EntityNotFoundException extends RuntimeException {

    private static final String PREFIX = "Error, Entity not found: ";

    private final String message;

    public EntityNotFoundException(String message) {
        super();
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
