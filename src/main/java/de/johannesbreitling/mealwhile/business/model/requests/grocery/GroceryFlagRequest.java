package de.johannesbreitling.mealwhile.business.model.requests.grocery;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GroceryFlagRequest {

    private final String name;
    private final String color;

}
