package de.johannesbreitling.mealwhile.business.controller;

import de.johannesbreitling.mealwhile.business.model.exceptions.BadRequestException;
import de.johannesbreitling.mealwhile.business.model.grocery.Grocery;
import de.johannesbreitling.mealwhile.business.model.requests.grocery.GroceryFlagRequest;
import de.johannesbreitling.mealwhile.business.model.requests.grocery.GroceryRequest;
import de.johannesbreitling.mealwhile.business.model.responses.grocery.GroceryResponse;
import de.johannesbreitling.mealwhile.business.model.responses.query.QueryMode;
import de.johannesbreitling.mealwhile.business.model.responses.query.SuccessfulQueryResponse;
import de.johannesbreitling.mealwhile.business.model.responses.grocery.GroceryFlagResponse;
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

    @GetMapping("/flags")
    public ResponseEntity<List<GroceryFlagResponse>> getAllGroceryFlags() {
        var flags = groceryService.getAllGroceryFlags();
        return ResponseEntity.ok(
                flags
                .stream()
                .map(
                     flag -> new GroceryFlagResponse(flag.getId(), flag.getName(), flag.getColor())
                ).collect(Collectors.toList())
        );
    }

    @PostMapping("/flags")
    public ResponseEntity<SuccessfulQueryResponse> createGroceryFlag(@RequestBody GroceryFlagRequest request) {
        if (request.getName() == null || request.getColor() == null) {
            throw new BadRequestException("Provide a name and a color for the grocery flag.");
        }

        var groceryFlag = groceryService.createGroceryFlag(request);
        var response = new SuccessfulQueryResponse("GroceryFlag", groceryFlag.getId(), QueryMode.CREATE);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/flags/{id}")
    public ResponseEntity<SuccessfulQueryResponse> updateGroceryFlag(
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
        var response = new SuccessfulQueryResponse("GroceryFlag", flag.getId(), QueryMode.UPDATE);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/flags/{id}")
    public ResponseEntity<SuccessfulQueryResponse> deleteGroceryFlag(@PathVariable String id) {
        if (id == null) {
            throw new BadRequestException("Provide a id for the grocery flag you want to delete.");
        }

        var flag = groceryService.deleteGroceryFlag(id);
        var response = new SuccessfulQueryResponse("GroceryFlag", flag.getId(), QueryMode.DELETE);

        return ResponseEntity.ok(response);
    }

    @GetMapping("")
    public List<GroceryResponse> getAllGroceries() {

        var groceries = groceryService.getAllGroceries();

        return groceries
                .stream()
                .map(grocery -> GroceryResponse
                        .builder()
                        .name(grocery.getName())
                        .id(grocery.getId())
                        .flags(
                                grocery
                                        .getFlags()
                                        .stream()
                                        .map(flag -> GroceryFlagResponse
                                                .builder()
                                                .color(flag.getColor())
                                                .name(flag.getName())
                                                .id(flag.getId())
                                                .build())
                                        .collect(Collectors.toList())
                        )
                        .defaultUnit(grocery.getUnit().toString())
                        .build())
                .collect(Collectors.toList());
    }

    @PostMapping("")
    public SuccessfulQueryResponse createGrocery(@RequestBody GroceryRequest request) {
        if (request.getName() == null && request.getUnit() == null) {
            throw new BadRequestException("Provide a name and a unit for the grocery.");
        }

        var grocery = groceryService.createGrocery(request);
        return new SuccessfulQueryResponse("Grocery", grocery.getId(), QueryMode.CREATE);
    }

    @PatchMapping("/{id}")
    public SuccessfulQueryResponse updateGrocery(
            @PathVariable String id,
            @RequestBody GroceryRequest request
    ) {
        if (id == null) {
            throw new BadRequestException("Provide a valid id for the grocery you want to update.");
        }

        if (request.getName() == null && request.getFlags() == null && request.getUnit() == null) {
            throw new BadRequestException("Arguments for updating a Grocery can't be empty.");
        }

        var grocery = groceryService.updateGrocery(id, request);

        return new SuccessfulQueryResponse("Grocery", grocery.getId(), QueryMode.UPDATE);
    }

    @DeleteMapping("/{id}")
    public SuccessfulQueryResponse deleteGrocery(@PathVariable String id) {
        if (id == null) {
            throw new BadRequestException("Provide a valid id for the grocery you want to update.");
        }

        var grocery = groceryService.deleteGrocery(id);

        return new SuccessfulQueryResponse("Grocery", grocery.getId(), QueryMode.DELETE);
    }

}
