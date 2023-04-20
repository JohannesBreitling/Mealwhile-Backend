package de.johannesbreitling.mealwhile.business.model.exceptions;

/**
 * Exception when a user tries to delete a group but the group still has members
 */
public class UserGroupNotEmptyException extends RuntimeException { }
