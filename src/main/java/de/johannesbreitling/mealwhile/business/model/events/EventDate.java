package de.johannesbreitling.mealwhile.business.model.events;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Embeddable
@NoArgsConstructor
public class EventDate {

    private int year;

    private int month;

    private int day;

    private int hours;

    private int minutes;

}
