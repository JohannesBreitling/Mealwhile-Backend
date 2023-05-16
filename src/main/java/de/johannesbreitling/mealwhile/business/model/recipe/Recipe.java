package de.johannesbreitling.mealwhile.business.model.recipe;

import de.johannesbreitling.mealwhile.business.model.responses.recipe.RecipeResponse;
import de.johannesbreitling.mealwhile.business.model.user.UserGroup;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "recipes")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Recipe {

    @Id
    @GenericGenerator(name = "recipe_id", strategy = "de.johannesbreitling.mealwhile.business.model.generator.MealwhileIdGenerator")
    @GeneratedValue(generator = "recipe_id")
    private String id;

    private String name;

    private String description;

    private String info;

    @ManyToOne
    private UserGroup accessGroup;

    @ElementCollection
    private List<Ingredient> ingredients;

    public RecipeResponse toResponse() {

        var convertedIngredients = ingredients
                .stream()
                .map(ingredient -> ingredient.toResponse())
                .toList();

        return RecipeResponse
                .builder()
                .id(id)
                .description(description)
                .info(info)
                .name(name)
                .ingredients(convertedIngredients)
                .build();
    }

}
