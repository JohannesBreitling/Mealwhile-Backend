package de.johannesbreitling.mealwhile.business.services;

import de.johannesbreitling.mealwhile.business.model.recipe.Ingredient;
import de.johannesbreitling.mealwhile.business.model.recipe.Recipe;
import de.johannesbreitling.mealwhile.business.model.requests.recipe.IngredientRequest;
import de.johannesbreitling.mealwhile.business.model.requests.recipe.RecipeRequest;
import de.johannesbreitling.mealwhile.business.model.user.UserGroup;
import de.johannesbreitling.mealwhile.business.repositories.RecipeRepository;
import de.johannesbreitling.mealwhile.business.services.interfaces.IAdminService;
import de.johannesbreitling.mealwhile.business.services.interfaces.IGroceryService;
import de.johannesbreitling.mealwhile.business.services.interfaces.IRecipeService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RecipeService implements IRecipeService {

    private final IAdminService userService;
    private final IGroceryService groceryService;

    private final RecipeRepository recipeRepository;

    public RecipeService(AdminService userService, GroceryService groceryService, RecipeRepository recipeRepository) {
        this.userService = userService;
        this.groceryService = groceryService;
        this.recipeRepository = recipeRepository;
    }

    private UserGroup getUserGroupByUsername(String username) {
        return userService.getUserGroupByUsername(username);
    }

    private String getUsernameFromToken() {
        var token = SecurityContextHolder.getContext().getAuthentication();
        return token.getName();
    }

    private List<Ingredient> convertIngredientRequests(List<IngredientRequest> requests) {

        List<Ingredient> ingredients = new ArrayList<>();

        for (var request : requests) {
            var grocery = groceryService.getGroceryById(request.getGroceryId());
            ingredients.add(new Ingredient(grocery, request.getQuantity()));
        }

        return ingredients;
    }

    @Override
    public Recipe createRecipe(RecipeRequest request) {

        var username = getUsernameFromToken();
        var userGroup = getUserGroupByUsername(username);
        var ingredients = convertIngredientRequests(request.getIngredients());

        var recipe = Recipe
                .builder()
                .name(request.getName())
                .description(request.getDescription())
                .info(request.getInfo())
                .accessGroup(userGroup)
                .ingredients(ingredients)
                .build();

        recipeRepository.save(recipe);

        return recipe;
    }


}
