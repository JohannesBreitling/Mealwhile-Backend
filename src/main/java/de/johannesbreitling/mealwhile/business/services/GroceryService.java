package de.johannesbreitling.mealwhile.business.services;

import de.johannesbreitling.mealwhile.business.model.exceptions.EntityNotFoundException;
import de.johannesbreitling.mealwhile.business.model.grocery.GroceryFlag;
import de.johannesbreitling.mealwhile.business.model.requests.grocery.GroceryFlagRequest;
import de.johannesbreitling.mealwhile.business.repositories.GroceryFlagRepository;
import de.johannesbreitling.mealwhile.business.services.interfaces.IGroceryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroceryService implements IGroceryService {

    private final GroceryFlagRepository groceryFlagRepository;

    public GroceryService(GroceryFlagRepository groceryFlagRepository) {
        this.groceryFlagRepository = groceryFlagRepository;
    }

    @Override
    public List<GroceryFlag> getAllGroceryFlags() {
        return groceryFlagRepository.findAll();
    }

    @Override
    public GroceryFlag createGroceryFlag(GroceryFlagRequest request) {

        GroceryFlag flag =
                GroceryFlag
                .builder()
                .color(request.getColor())
                .name(request.getName())
                .build();

        groceryFlagRepository.save(flag);

        return flag;
    }

    @Override
    public GroceryFlag updateGroceryFlag(String id, GroceryFlagRequest request) {

        var foundFlag = groceryFlagRepository.findFlagById(id);
        if (foundFlag.isEmpty()) {
            throw new EntityNotFoundException("Grocery flag with the given id does not exist.");
        }

        var flag = foundFlag.get();

        if (request.getName() != null) {
            flag.setName(request.getName());
        }

        if (request.getColor() != null) {
            flag.setColor(request.getColor());
        }

        groceryFlagRepository.save(flag);

        return flag;
    }

    @Override
    public GroceryFlag deleteGroceryFlag(String id) {
        var foundFlag = groceryFlagRepository.findFlagById(id);
        if (foundFlag.isEmpty()) {
            throw new EntityNotFoundException("Grocery flag with the given id does not exist.");
        }

        groceryFlagRepository.delete(foundFlag.get());

        return foundFlag.get();
    }
}
