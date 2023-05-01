package de.johannesbreitling.mealwhile.business.controller;

import de.johannesbreitling.mealwhile.business.model.exceptions.BadRequestException;
import de.johannesbreitling.mealwhile.business.model.exceptions.EntityNotFoundException;
import de.johannesbreitling.mealwhile.business.model.responses.ApiError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(value = EntityNotFoundException.class)
    public ResponseEntity<ApiError> handleEntityNotFound(EntityNotFoundException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ApiError("404", e.getMessage()));
    }

    @ExceptionHandler(value = BadRequestException.class)
    public ResponseEntity<ApiError> handleEntityNotFound(BadRequestException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ApiError("400", e.getMessage()));
    }

}
