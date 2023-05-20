package de.johannesbreitling.mealwhile.business.repositories;

import de.johannesbreitling.mealwhile.business.model.events.ParticipantProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ParticipantProfileRepository extends JpaRepository<ParticipantProfile, String> {
    Optional<ParticipantProfile> findParticipantProfileById(String id);
}
