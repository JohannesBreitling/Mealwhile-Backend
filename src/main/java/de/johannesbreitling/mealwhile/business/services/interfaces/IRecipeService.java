package de.johannesbreitling.mealwhile.business.services.interfaces;

import de.johannesbreitling.mealwhile.business.model.recipe.Recipe;
import de.johannesbreitling.mealwhile.business.model.requests.recipe.RecipeRequest;

import java.util.List;

public interface IRecipeService {

    List<Recipe> getAllRecipesByGroup(String groupId);
    Recipe createRecipe(RecipeRequest request);
    //Recipe updateRecipe(String id, RecipeRequest request);
    //Recipe deleteRecipe(String id);

}
