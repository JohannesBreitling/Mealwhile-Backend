package de.johannesbreitling.mealwhile.business.model.exceptions;

/**
 * Exception when an entity that should be created already exists
 */
public class EntityAlreadyExistsException extends RuntimeException {

    private static final String PREFIX = "Error, entity already exists: ";

    private final String entity;

    public EntityAlreadyExistsException(String entity) {
        super();
        this.entity = entity;
    }

    public String getMessage() {
        return PREFIX + entity;
    }

}
