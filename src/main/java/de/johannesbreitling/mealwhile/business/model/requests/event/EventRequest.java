package de.johannesbreitling.mealwhile.business.model.requests.event;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EventRequest {

    private final String name;

    private final String description;

    private final String startDate;

    private final String endDate;

}
