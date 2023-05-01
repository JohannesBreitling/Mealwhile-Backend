package de.johannesbreitling.mealwhile.business.services.interfaces;

import de.johannesbreitling.mealwhile.business.model.grocery.GroceryFlag;
import de.johannesbreitling.mealwhile.business.model.requests.grocery.GroceryFlagRequest;

import java.util.List;

public interface IGroceryService {

    // Methods regarding the grocery flags
    List<GroceryFlag> getAllGroceryFlags();
    GroceryFlag createGroceryFlag(GroceryFlagRequest request);
    GroceryFlag updateGroceryFlag(String id, GroceryFlagRequest request);
    GroceryFlag deleteGroceryFlag(String id);

}
