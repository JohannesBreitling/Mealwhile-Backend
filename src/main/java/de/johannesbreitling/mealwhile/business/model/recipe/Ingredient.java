package de.johannesbreitling.mealwhile.business.model.recipe;

import de.johannesbreitling.mealwhile.business.model.grocery.Grocery;
import de.johannesbreitling.mealwhile.business.model.responses.recipe.IngredientResponse;
import jakarta.persistence.Embeddable;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class Ingredient {

    @OneToOne
    private Grocery grocery;

    private float quantity;

    public IngredientResponse toResponse() {
        return IngredientResponse
                .builder()
                .grocery(grocery.toResponse())
                .quantity(quantity)
                .build();
    }

}
