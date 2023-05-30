package de.johannesbreitling.mealwhile.business.repositories;

import de.johannesbreitling.mealwhile.business.model.events.ScheduledMeal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduledMealRepository extends JpaRepository<ScheduledMeal, String> {



}
