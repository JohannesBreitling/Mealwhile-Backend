package de.johannesbreitling.mealwhile.business.repositories;

import de.johannesbreitling.mealwhile.business.model.user.UserGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository for the user groups
 */
public interface UserGroupRepository extends JpaRepository<UserGroup, String> {

    Optional<UserGroup> findGroupByName(String name);
    Optional<UserGroup> findGroupById(String id);

}
