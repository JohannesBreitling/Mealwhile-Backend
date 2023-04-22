package de.johannesbreitling.mealwhile.business.repositories;

import de.johannesbreitling.mealwhile.business.model.user.User;
import de.johannesbreitling.mealwhile.business.model.user.UserGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for the users of the application
 */
public interface UserRepository extends JpaRepository<User, Integer> {

    // Username is unique
    Optional<User> findUserByUsername(String username);

    Optional<User> findUserById(String id);

    // Get all users with the specified group
    Optional<List<User>> findUsersByGroup(UserGroup group);

}
