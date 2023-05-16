package de.johannesbreitling.mealwhile.business.model.responses.recipe;

import de.johannesbreitling.mealwhile.business.model.responses.grocery.GroceryResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class IngredientResponse {

    private final GroceryResponse grocery;
    private final float quantity;

}
