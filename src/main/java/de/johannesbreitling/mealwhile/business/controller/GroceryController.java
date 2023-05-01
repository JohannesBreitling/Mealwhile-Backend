package de.johannesbreitling.mealwhile.business.controller;

import de.johannesbreitling.mealwhile.business.model.exceptions.BadRequestException;
import de.johannesbreitling.mealwhile.business.model.requests.grocery.GroceryFlagRequest;
import de.johannesbreitling.mealwhile.business.model.responses.IdOnlyResonse;
import de.johannesbreitling.mealwhile.business.model.responses.grocery.GroceryFlagResponse;
import de.johannesbreitling.mealwhile.business.model.responses.grocery.GroceryResponse;
import de.johannesbreitling.mealwhile.business.services.GroceryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/groceries")
public class GroceryController {

    private final GroceryService groceryService;

    public GroceryController(GroceryService groceryService) {
        this.groceryService = groceryService;
    }

    @GetMapping("/flagss")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("HALLO!");
    }

    @GetMapping("/flags")
    public ResponseEntity<List<GroceryFlagResponse>> getAllGroceryFlags() {
        var flags = groceryService.getAllGroceryFlags();
        return ResponseEntity.ok(
                flags
                .stream()
                .map(
                     flag -> new GroceryFlagResponse(flag.getName(), flag.getColor())
                ).collect(Collectors.toList())
        );
    }

    @PostMapping("/flags")
    public ResponseEntity<GroceryFlagResponse> createGroceryFlag(@RequestBody GroceryFlagRequest request) {
        if (request.getName() == null || request.getColor() == null) {
            throw new BadRequestException("Provide a name and a color for the grocery flag.");
        }

        var groceryFlag = groceryService.createGroceryFlag(request);

        return ResponseEntity.ok(new GroceryFlagResponse(groceryFlag.getName(), groceryFlag.getColor()));
    }

    @PatchMapping("/flags/{id}")
    public ResponseEntity<IdOnlyResonse> updateGroceryFlag(
            @PathVariable String id,
            @RequestBody GroceryFlagRequest request
            ) {
        if (request.getColor() == null && request.getName() == null) {
            throw new BadRequestException("Arguments for updating a grocery flag can't be empty.");
        }

        if (id == null) {
            throw new BadRequestException("Provide a id for the grocery flag you want to update.");
        }

        var flag = groceryService.updateGroceryFlag(id, request);
        return ResponseEntity.ok(new IdOnlyResonse(flag.getId()));
    }

    @DeleteMapping("/flags/{id}")
    public ResponseEntity<IdOnlyResonse> updateGroceryFlag(@PathVariable String id) {

        if (id == null) {
            throw new BadRequestException("Provide a id for the grocery flag you want to delete.");
        }

        var flag = groceryService.deleteGroceryFlag(id);
        return ResponseEntity.ok(new IdOnlyResonse(flag.getId()));
    }

}
