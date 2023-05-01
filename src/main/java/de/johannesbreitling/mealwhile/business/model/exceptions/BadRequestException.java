package de.johannesbreitling.mealwhile.business.model.exceptions;

public class BadRequestException extends RuntimeException {

    private static final String PREFIX = "Error, bad request: ";

    private final String message;

    public BadRequestException(String message) {
        super();
        this.message = message;
    }

    public String getMessage() {
        return PREFIX + this.message;
    }

}
