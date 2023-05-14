package de.johannesbreitling.mealwhile.business.model.recipe;

import de.johannesbreitling.mealwhile.business.model.user.UserGroup;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;

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

}
