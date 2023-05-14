package de.johannesbreitling.mealwhile.business.controller;

import de.johannesbreitling.mealwhile.business.model.exceptions.BadRequestException;
import de.johannesbreitling.mealwhile.business.model.requests.recipe.RecipeRequest;
import de.johannesbreitling.mealwhile.business.model.responses.query.QueryMode;
import de.johannesbreitling.mealwhile.business.model.responses.query.SuccessfulQueryResponse;
import de.johannesbreitling.mealwhile.business.services.RecipeService;
import de.johannesbreitling.mealwhile.business.services.interfaces.IRecipeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

}
