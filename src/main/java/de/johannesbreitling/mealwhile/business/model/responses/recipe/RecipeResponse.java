package de.johannesbreitling.mealwhile.business.model.responses.recipe;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class RecipeResponse {

    private final String id;
    private final String name;
    private final List<IngredientResponse> ingredients;
    private final String info;
    private final String description;

}
