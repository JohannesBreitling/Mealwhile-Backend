package de.johannesbreitling.mealwhile.business.services.interfaces;

import de.johannesbreitling.mealwhile.business.model.requests.admin.UserGroupRequest;
import de.johannesbreitling.mealwhile.business.model.requests.admin.UserRequest;
import de.johannesbreitling.mealwhile.business.model.user.User;
import de.johannesbreitling.mealwhile.business.model.user.UserGroup;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface IAdminService {

    // Methods regarding the token
    UserGroup getUserGroupFromToken();

    // Methods regarding the user groups
    List<UserGroup> getAllUserGroups();
    UserGroup getGroupById(String id);
    UserGroup createUserGroup(UserGroupRequest request);
    UserGroup updateUserGroup(String id, UserGroupRequest request);
    UserGroup deleteUserGroup(String id);

    // Methods regarding the users
    List<User> getAllUsers();
    List<User> getUsersByGroup(String groupId);
    UserGroup getUserGroupByUsername(String name);
    User updateUser(String id, UserRequest request);
    User deleteUser(String id);

}
