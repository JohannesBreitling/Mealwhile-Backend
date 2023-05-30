package de.johannesbreitling.mealwhile.business.model.events;

import de.johannesbreitling.mealwhile.business.model.events.eventdate.EventDate;
import de.johannesbreitling.mealwhile.business.model.recipe.Recipe;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;

@Entity
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

    @ElementCollection
    private List<LinkedRecipe> meals;

}
