package de.johannesbreitling.mealwhile.business.model.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for the users of the application
 */
public interface UserRepository extends JpaRepository<User, Integer> {

    // Username is unique
    Optional<User> findUserByUsername(String username);

    // Get all users with the specified group
    Optional<List<User>> findUsersByGroup(UserGroup group);

}
