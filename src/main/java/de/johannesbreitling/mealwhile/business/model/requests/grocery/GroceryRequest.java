package de.johannesbreitling.mealwhile.business.model.requests.grocery;

import de.johannesbreitling.mealwhile.business.model.grocery.GroceryUnit;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class GroceryRequest {

    private final String name;
    private final List<String> flags;
    private final String unit;

}
