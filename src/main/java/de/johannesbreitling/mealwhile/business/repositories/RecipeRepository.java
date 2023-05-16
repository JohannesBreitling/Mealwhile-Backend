package de.johannesbreitling.mealwhile.business.repositories;

import de.johannesbreitling.mealwhile.business.model.recipe.Recipe;
import de.johannesbreitling.mealwhile.business.model.user.UserGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RecipeRepository extends JpaRepository<Recipe, String> {

    Optional<List<Recipe>> findRecipesByAccessGroup(UserGroup accessGroup);

}
