package de.johannesbreitling.mealwhile.business.model.events;

import de.johannesbreitling.mealwhile.business.model.grocery.GroceryFlag;
import jakarta.persistence.Embeddable;
import jakarta.persistence.ManyToMany;

import java.util.List;

@Embeddable
public class ParticipantProfile {

    @ManyToMany
    private List<GroceryFlag> allergies;

    private int number;

}
