package de.johannesbreitling.mealwhile.business.model.events;

import de.johannesbreitling.mealwhile.business.model.recipe.Recipe;
import jakarta.persistence.Embeddable;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LinkedRecipe {

    @OneToOne
    private Recipe recipe;

    @OneToOne
    private ParticipantProfile profile;

}
