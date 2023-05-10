package de.johannesbreitling.mealwhile.business.model.responses.grocery;

import de.johannesbreitling.mealwhile.business.model.responses.IApiResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class GroceryResponse implements IApiResponse {

    private final String id;
    private final String name;
    private final List<GroceryFlagResponse> flags;
    private final String defaultUnit;

}
