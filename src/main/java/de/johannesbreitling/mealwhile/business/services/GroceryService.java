package de.johannesbreitling.mealwhile.business.services;

import de.johannesbreitling.mealwhile.business.model.exceptions.BadRequestException;
import de.johannesbreitling.mealwhile.business.model.exceptions.EntityAlreadyExistsException;
import de.johannesbreitling.mealwhile.business.model.exceptions.EntityNotFoundException;
import de.johannesbreitling.mealwhile.business.model.grocery.Grocery;
import de.johannesbreitling.mealwhile.business.model.grocery.GroceryFlag;
import de.johannesbreitling.mealwhile.business.model.grocery.GroceryUnit;
import de.johannesbreitling.mealwhile.business.model.requests.grocery.GroceryFlagRequest;
import de.johannesbreitling.mealwhile.business.model.requests.grocery.GroceryRequest;
import de.johannesbreitling.mealwhile.business.repositories.GroceryFlagRepository;
import de.johannesbreitling.mealwhile.business.repositories.GroceryRepository;
import de.johannesbreitling.mealwhile.business.services.interfaces.IGroceryService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GroceryService implements IGroceryService {

    private final GroceryFlagRepository groceryFlagRepository;
    private final GroceryRepository groceryRepository;

    public GroceryService(GroceryFlagRepository groceryFlagRepository, GroceryRepository groceryRepository) {
        this.groceryFlagRepository = groceryFlagRepository;
        this.groceryRepository = groceryRepository;
    }

    private Grocery getGroceryFromRepository(String id) {
        var grocery = groceryRepository.findGroceryById(id);
        if (grocery.isEmpty()) {
            throw new EntityNotFoundException("Grocery with id " + id);
        }

        return grocery.get();
    }

    private GroceryFlag getGroceryFlagFromRepository(String id) {
        var groceryFlag = groceryFlagRepository.findFlagById(id);
        if (groceryFlag.isEmpty()) {
            throw new EntityNotFoundException("GroceryFlag with id " + id);
        }

        return groceryFlag.get();
    }

    private List<GroceryFlag> getFlagsFromRequest(GroceryRequest request) {
        return request
                .getFlags()
                .stream()
                .map(flagId -> getGroceryFlagFromRepository(flagId))
                .collect(Collectors.toList());
    }

    @Override
    public List<GroceryFlag> getAllGroceryFlags() {
        return groceryFlagRepository.findAll();
    }

    @Override
    public GroceryFlag createGroceryFlag(GroceryFlagRequest request) {

        var foundFlag = groceryFlagRepository.findFlagByName(request.getName());
        if (foundFlag.isPresent()) {
            throw new EntityAlreadyExistsException("GroceryFlag " + request.getName());
        }

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
        var flag = getGroceryFlagFromRepository(id);

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
        var flag = getGroceryFlagFromRepository(id);
        groceryFlagRepository.delete(flag);
        return flag;
    }

    @Override
    public List<Grocery> getAllGroceries() {
        return groceryRepository.findAll();
    }

    @Override
    public Grocery createGrocery(GroceryRequest request) {
        var foundGrocery = groceryRepository.findGroceryByName(request.getName());
        if (foundGrocery.isPresent()) {
            throw new EntityAlreadyExistsException("Grocery " + request.getName());
        }

        List<GroceryFlag> flags = new ArrayList<>();
        if (request.getFlags() != null) {
            flags = getFlagsFromRequest(request);
        }

        try {
            var grocery = Grocery
                    .builder()
                    .unit(GroceryUnit.valueOf(request.getUnit()))
                    .name(request.getName())
                    .flags(flags)
                    .build();
            groceryRepository.save(grocery);
            return grocery;
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("Unit " + request.getUnit() + " is not valid.");
        }
    }

    @Override
    public Grocery updateGrocery(String id, GroceryRequest groceryRequest) {
        var grocery = getGroceryFromRepository(id);

        if (groceryRequest.getFlags() != null) {
            var newFlags = getFlagsFromRequest(groceryRequest);
            grocery.setFlags(newFlags);
        }

        if (groceryRequest.getName() != null) {
            grocery.setName(groceryRequest.getName());
        }

        if (groceryRequest.getUnit() != null) {
            try {
                grocery.setUnit(GroceryUnit.valueOf(groceryRequest.getUnit()));
            } catch (IllegalArgumentException e) {
                throw new BadRequestException("Unit " + groceryRequest.getUnit() + " is not valid.");
            }
        }

        groceryRepository.save(grocery);
        return grocery;
    }

    public Grocery deleteGrocery(String id) {
        var grocery = getGroceryFromRepository(id);

        groceryRepository.delete(grocery);
        return grocery;
    }

    @Override
    public Grocery getGroceryById(String id) {
        return getGroceryFromRepository(id);
    }
    
}
