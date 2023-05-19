package de.johannesbreitling.mealwhile.business.model.events;


import de.johannesbreitling.mealwhile.business.model.recipe.Ingredient;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;

//@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Event {

    @Id
    @GenericGenerator(name = "event_id", strategy = "de.johannesbreitling.mealwhile.business.model.generator.MealwhileIdGenerator")
    @GeneratedValue(generator = "event_id")
    private String id;

    private String name;

    private String description;

    private EventDate startDate;

    private EventDate endDate;

    @ElementCollection
    private List<ScheduledMeal> meals;

    @ElementCollection
    private List<ParticipantProfile> profiles;

    @ElementCollection
    private List<Ingredient> storage;

}
