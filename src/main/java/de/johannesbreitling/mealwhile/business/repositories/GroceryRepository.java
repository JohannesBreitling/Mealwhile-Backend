package de.johannesbreitling.mealwhile.business.repositories;

import de.johannesbreitling.mealwhile.business.model.grocery.Grocery;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GroceryRepository extends JpaRepository<Grocery, String> {

    Optional<Grocery> findGroceryByName(String name);
    Optional<Grocery> findGroceryById(String id);

}
