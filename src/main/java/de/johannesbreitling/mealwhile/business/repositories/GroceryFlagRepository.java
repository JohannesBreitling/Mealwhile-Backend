package de.johannesbreitling.mealwhile.business.repositories;

import de.johannesbreitling.mealwhile.business.model.grocery.GroceryFlag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GroceryFlagRepository extends JpaRepository<GroceryFlag, String> {

    Optional<GroceryFlag> findFlagByName(String name);
    Optional<GroceryFlag> findFlagById(String id);

}
