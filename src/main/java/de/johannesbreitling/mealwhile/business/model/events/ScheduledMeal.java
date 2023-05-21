package de.johannesbreitling.mealwhile.business.model.events;

import de.johannesbreitling.mealwhile.business.model.events.eventdate.EventDate;
import de.johannesbreitling.mealwhile.business.model.recipe.Recipe;
import jakarta.persistence.Embeddable;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

@Embeddable
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ScheduledMeal {

    @Id
    @GenericGenerator(name = "meal_id", strategy = "de.johannesbreitling.mealwhile.business.model.generator.MealwhileIdGenerator")
    @GeneratedValue(generator = "meal_id")
    private String id;

    private EventDate date;

    @ManyToOne
    private Recipe meal;

}
