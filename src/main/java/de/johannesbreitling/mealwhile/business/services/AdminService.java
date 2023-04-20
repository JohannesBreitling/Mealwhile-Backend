package de.johannesbreitling.mealwhile.business.services;

import de.johannesbreitling.mealwhile.business.model.exceptions.EntityAlreadyExistsException;
import de.johannesbreitling.mealwhile.business.model.exceptions.EntityNotFoundException;
import de.johannesbreitling.mealwhile.business.model.exceptions.UserGroupNotEmptyException;
import de.johannesbreitling.mealwhile.business.model.requests.admin.UserGroupRequest;
import de.johannesbreitling.mealwhile.business.model.user.User;
import de.johannesbreitling.mealwhile.business.model.user.UserGroup;
import de.johannesbreitling.mealwhile.business.model.user.UserRepository;
import de.johannesbreitling.mealwhile.business.repositories.UserGroupRepository;
import de.johannesbreitling.mealwhile.business.services.interfaces.IAdminService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService implements IAdminService {

    private final UserGroupRepository userGroupRepository;
    private final UserRepository userRepository;

    public AdminService(UserGroupRepository userGroupRepository, UserRepository userRepository) {
        this.userGroupRepository = userGroupRepository;
        this.userRepository = userRepository;
    }

    @Override
    public UserGroup createUserGroup(UserGroupRequest request) {

        // Check if the group already exists
        var foundGroup = userGroupRepository.findGroupByName(request.getName());

        if (foundGroup.isPresent()) {
            throw new EntityAlreadyExistsException();
        }

        // Group does not exist
        UserGroup group = UserGroup.builder().color(request.getColor()).name(request.getName()).build();

        userGroupRepository.save(group);

        return group;
    }

    @Override
    public UserGroup updateUserGroup(String id, UserGroupRequest request) {

        // Get the group based on the given id
        var group = userGroupRepository.findGroupById(id);

        if (group.isEmpty()) {
            throw new EntityNotFoundException();
        }

        UserGroup foundGroup = group.get();

        if (request.getName() != null) {
            foundGroup.setName(request.getName());
        }

        if (request.getColor() != null) {
            foundGroup.setColor(request.getColor());
        }

        userGroupRepository.save(foundGroup);

        return foundGroup;
    }

    @Override
    public UserGroup deleteUserGroup(String id) {

        // Get group based on the given id
        var group = userGroupRepository.findGroupById(id);

        if (group.isEmpty()) {
            throw new EntityNotFoundException();
        }

        UserGroup foundGroup = group.get();

        var users = userRepository.findUsersByGroup(foundGroup);
        if (users.isPresent() && !users.get().isEmpty()) {
            throw new UserGroupNotEmptyException();
        }

        userGroupRepository.delete(foundGroup);

        return foundGroup;
    }

}
