package de.johannesbreitling.mealwhile.business.model.responses.grocery;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class GroceryFlagResponse {

    private final String id;
    private final String name;
    private final String color;

}
