package de.johannesbreitling.mealwhile.business.model.requests.recipe;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class RecipeRequest {

    private final String name;
    private final String description;
    private final String info;
    private final List<IngredientRequest> ingredients;

}
