package de.johannesbreitling.mealwhile.business.model.responses.grocery;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GroceryFlagResponse {

    private final String name;
    private final String color;

}
