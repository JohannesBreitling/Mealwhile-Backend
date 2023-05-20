package de.johannesbreitling.mealwhile.business.repositories;

import de.johannesbreitling.mealwhile.business.model.events.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, String> {
    Optional<Event> findEventById(String id);
}
