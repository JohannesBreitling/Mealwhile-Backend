package de.johannesbreitling.mealwhile.business.model.responses;

public record ApiError(String code, String message) implements IApiResponse { }
