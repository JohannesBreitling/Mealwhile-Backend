package de.johannesbreitling.mealwhile.business.controller;

import de.johannesbreitling.mealwhile.business.model.exceptions.BadRequestException;
import de.johannesbreitling.mealwhile.business.model.requests.recipe.RecipeRequest;
import de.johannesbreitling.mealwhile.business.model.responses.query.QueryMode;
import de.johannesbreitling.mealwhile.business.model.responses.query.SuccessfulQueryResponse;
import de.johannesbreitling.mealwhile.business.model.responses.recipe.RecipeResponse;
import de.johannesbreitling.mealwhile.business.services.RecipeService;
import de.johannesbreitling.mealwhile.business.services.interfaces.IRecipeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/recipes")
public class RecipeController {

    private final IRecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @PostMapping("")
    public ResponseEntity<SuccessfulQueryResponse> createRecipe(@RequestBody RecipeRequest request) {
        if (request.getName() == null
                && request.getDescription() == null
                && request.getInfo() == null
                && (request.getIngredients() == null || request.getIngredients().isEmpty())
        ) {
            throw new BadRequestException("Provide valid arguments to create a recipe.");
        }

        var recipe = recipeService.createRecipe(request);
        return ResponseEntity.ok(new SuccessfulQueryResponse("Recipe", recipe.getId(), QueryMode.CREATE));
    }

    @GetMapping("/group/{groupId}")
    public List<RecipeResponse> getRecipesByAccessGroup(@PathVariable String groupId) {
        if (groupId == null) {
            throw new BadRequestException("GroupId can not be null");
        }

        var recipes = recipeService.getAllRecipesByGroup(groupId);
        return recipes
                .stream()
                .map(
                        recipe -> recipe.toResponse()
                ).toList();
    }

    @PatchMapping("/{recipeId}")
    public RecipeResponse updateRecipe(@PathVariable String recipeId,
                                       @RequestBody RecipeRequest request) {
        if (recipeId == null) {
            throw new BadRequestException("Provide a id for the recipe you want to update");
        }

        if (request == null
                || (
                        request.getIngredients() == null
                && request.getInfo() == null
                && request.getDescription() == null
                && request.getName() == null
        )) {
            throw new BadRequestException("Arguments to update recipe can not be null");
        }

        var recipe = recipeService.updateRecipe(recipeId, request);
        return recipe.toResponse();
    }


    @DeleteMapping("/{recipeId}")
    public RecipeResponse deleteRecipe(@PathVariable String recipeId) {
        if (recipeId == null) {
            throw new BadRequestException("Provide a id for the recipe you want to delete.");
        }

        var deletedRecipe = recipeService.deleteRecipe(recipeId);
        return deletedRecipe.toResponse();
    }

}
