package de.johannesbreitling.mealwhile.business.model.responses.grocery;

import de.johannesbreitling.mealwhile.business.model.responses.IApiResponse;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GroceryResponse implements IApiResponse {

    private final String name;
    private final String defaultUnit;

}
