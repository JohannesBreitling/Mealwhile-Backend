package de.johannesbreitling.mealwhile.business.model.requests.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class ScheduledMealRequest {

    private final String name;

    private final String date;

    private final List<LinkedRecipeRequest> recipes;

}
