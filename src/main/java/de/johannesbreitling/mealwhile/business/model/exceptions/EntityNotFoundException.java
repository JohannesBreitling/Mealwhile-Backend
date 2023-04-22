package de.johannesbreitling.mealwhile.business.model.exceptions;

/**
 * Exception when a given Entity is not found in the repository
 */
public class EntityNotFoundException extends RuntimeException {
    private final String entity;

    public EntityNotFoundException() {
        entity = null;
    }

    public EntityNotFoundException(String entity) {
        this.entity = entity;
    }

    public String getEntity() {
        return entity;
    }
}
