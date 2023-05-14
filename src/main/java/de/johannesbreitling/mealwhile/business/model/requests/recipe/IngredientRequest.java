package de.johannesbreitling.mealwhile.business.model.requests.recipe;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class IngredientRequest {

    private final String groceryId;
    private final float quantity;

}
