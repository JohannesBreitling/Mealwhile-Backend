package de.johannesbreitling.mealwhile.business.model.requests.event;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LinkedRecipeRequest {

    private String recipeId;

    private String profileId;

}
