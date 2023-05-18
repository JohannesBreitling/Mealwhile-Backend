package de.johannesbreitling.mealwhile.business.services;

import de.johannesbreitling.mealwhile.business.model.exceptions.AccessNotAllowedException;
import de.johannesbreitling.mealwhile.business.model.exceptions.EntityNotFoundException;
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
        System.out.println("RETURN GROUP");
        return userService.getUserGroupByUsername(username);
    }

    private String getUsernameFromToken() {
        var token = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("RETURN TOKEN");
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
    public List<Recipe> getAllRecipesByGroup(String groupId) {
        var group = userService.getGroupById(groupId);

        var recipes = recipeRepository.findRecipesByAccessGroup(group);
        if (recipes.isEmpty()) {
            return new ArrayList<>();
        }

        return recipes.get();
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

    public Recipe updateRecipe(String id, RecipeRequest request) {

        var foundRecipe = recipeRepository.findById(id);

        if (foundRecipe.isEmpty()) {
            throw new EntityNotFoundException("Recipe with id " + id);
        }
        var recipe = foundRecipe.get();

        var username = getUsernameFromToken();
        var userGroup = getUserGroupByUsername(username);

        if (!recipe.getAccessGroup().equals(userGroup)) {
            throw new AccessNotAllowedException("Recipe with id " + id + " not known for group of the user");
        }

        if (request.getName() != null) {
            recipe.setName(request.getName());
        }

        if (request.getInfo() != null) {
            recipe.setInfo(request.getInfo());
        }

        if (request.getDescription() != null) {
            recipe.setDescription(request.getDescription());
        }

        if (request.getIngredients() != null) {
            var newIngredients = convertIngredientRequests(request.getIngredients());
            recipe.setIngredients(newIngredients);
        }

        recipeRepository.save(recipe);
        return recipe;
    }

    @Override
    public Recipe deleteRecipe(String id) {

        var recipe = recipeRepository.findById(id);

        if (recipe.isEmpty()) {
            throw new EntityNotFoundException("Recipe with id " + id);
        }

        // Get userGroup of user
        var username = getUsernameFromToken();
        var group = getUserGroupByUsername(username);

        if (!recipe.get().getAccessGroup().equals(group)) {
            throw new AccessNotAllowedException("Recipe with id " + id + " not known for group of the user");
        }

        recipeRepository.delete(recipe.get());
        return recipe.get();
    }


}
