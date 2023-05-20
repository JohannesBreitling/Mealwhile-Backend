package de.johannesbreitling.mealwhile.business.model.responses.event;

import de.johannesbreitling.mealwhile.business.model.responses.grocery.GroceryFlagResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import java.util.List;


@Data
@Builder
@AllArgsConstructor
public class ParticipantProfileResponse {

    private final String id;
    private final List<GroceryFlagResponse> flags;
    private final int number;

}
