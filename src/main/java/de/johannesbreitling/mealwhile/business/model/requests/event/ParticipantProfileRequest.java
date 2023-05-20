package de.johannesbreitling.mealwhile.business.model.requests.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Builder
@Data
public class ParticipantProfileRequest {

    private final List<String> flags;
    private final int number;

}
