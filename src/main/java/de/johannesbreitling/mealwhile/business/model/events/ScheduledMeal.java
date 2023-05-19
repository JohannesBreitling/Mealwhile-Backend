package de.johannesbreitling.mealwhile.business.model.events;

import de.johannesbreitling.mealwhile.business.model.recipe.Recipe;
import jakarta.persistence.Embeddable;
import jakarta.persistence.ManyToOne;

@Embeddable
public class ScheduledMeal {

    private EventDate date;

    @ManyToOne
    private Recipe meal;

}
