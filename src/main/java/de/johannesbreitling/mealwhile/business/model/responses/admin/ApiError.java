package de.johannesbreitling.mealwhile.business.model.responses.admin;

public record ApiError(String code, String message) implements ApiResponse { }
