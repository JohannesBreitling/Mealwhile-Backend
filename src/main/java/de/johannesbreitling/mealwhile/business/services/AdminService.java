package de.johannesbreitling.mealwhile.business.services;

import de.johannesbreitling.mealwhile.business.model.exceptions.EntityAlreadyExistsException;
import de.johannesbreitling.mealwhile.business.model.exceptions.EntityNotFoundException;
import de.johannesbreitling.mealwhile.business.model.exceptions.UserGroupNotEmptyException;
import de.johannesbreitling.mealwhile.business.model.requests.admin.UserGroupRequest;
import de.johannesbreitling.mealwhile.business.model.requests.admin.UserRequest;
import de.johannesbreitling.mealwhile.business.model.user.User;
import de.johannesbreitling.mealwhile.business.model.user.UserGroup;
import de.johannesbreitling.mealwhile.business.repositories.UserRepository;
import de.johannesbreitling.mealwhile.business.repositories.UserGroupRepository;
import de.johannesbreitling.mealwhile.business.services.interfaces.IAdminService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService implements IAdminService {

    private final UserGroupRepository userGroupRepository;
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public AdminService(UserGroupRepository userGroupRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userGroupRepository = userGroupRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<UserGroup> getAllUserGroups() {
        return userGroupRepository.findAll();
    }

    public UserGroup getGroupById(String id) {
        var group = userGroupRepository.findGroupById(id);
        if (group.isEmpty()) {
            throw new EntityNotFoundException("User group with id " + id);
        }

        return group.get();
    }

    @Override
    public UserGroup createUserGroup(UserGroupRequest request) {

        // Check if the group already exists
        var foundGroup = userGroupRepository.findGroupByName(request.getName());

        if (foundGroup.isPresent()) {
            throw new EntityAlreadyExistsException("UserGroup " + request.getName());
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
            throw new EntityNotFoundException("User group with the given id does not exist.");
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
            throw new EntityNotFoundException("User group with the given id does not exist.");
        }

        UserGroup foundGroup = group.get();

        var users = userRepository.findUsersByGroup(foundGroup);
        if (users.isPresent() && !users.get().isEmpty()) {
            throw new UserGroupNotEmptyException();
        }

        userGroupRepository.delete(foundGroup);

        return foundGroup;
    }

    @Override
    public UserGroup getUserGroupByUsername(String name) {
        var user = userRepository.findUserByUsername(name);

        if (user.isEmpty()) {
            throw new EntityNotFoundException("User with the name " + name);
        }

        return user.get().getGroup();
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public List<User> getUsersByGroup(String groupId) {
        var foundGroup = userGroupRepository.findGroupById(groupId);
        if (foundGroup.isEmpty()) {
            throw new EntityNotFoundException("User group with the given id doest not exist.");
        }

        var group = foundGroup.get();
        var users = userRepository.findUsersByGroup(group);

        return users.isPresent() ? users.get() : null;
    }

    @Override
    public User updateUser(String id, UserRequest request) {

        var foundUser = userRepository.findUserById(id);

        if (foundUser.isEmpty()) {
            throw new EntityNotFoundException("User with the given id does not exist.");
        }

        var user = foundUser.get();

        if (request.getUsername() != null) {
            // Make sure the username is unique
            var existingUser = userRepository.findUserByUsername(request.getUsername());

            if (existingUser.isPresent()) {
                throw new EntityAlreadyExistsException("User " + request.getUsername());
            }

            user.setUsername(request.getUsername());
        }

        if (request.getFirstname() != null) {
            user.setFirstname(request.getFirstname());
        }

        if (request.getLastname() != null) {
            user.setLastname(request.getLastname());
        }

        if (request.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        if (request.getGroupId() != null) {
            // Get the requested group
            var foundGroup = userGroupRepository.findGroupById(request.getGroupId());

            if (foundGroup.isEmpty()) {
                throw new EntityNotFoundException("User group with the given id does not exist.");
            }

            user.setGroup(foundGroup.get());
        }

        userRepository.save(user);

        return user;
    }

    @Override
    public User deleteUser(String id) {

        var foundUser = userRepository.findUserById(id);

        if (foundUser.isEmpty()) {
            throw new EntityNotFoundException("User with the given id doest not exist.");
        }

        User user = foundUser.get();
        userRepository.delete(user);

        return user;
    }

}
