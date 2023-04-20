package de.johannesbreitling.mealwhile.business.services.interfaces;

import de.johannesbreitling.mealwhile.business.model.requests.admin.UserGroupRequest;
import de.johannesbreitling.mealwhile.business.model.user.UserGroup;
import org.springframework.stereotype.Component;

@Component
public interface IAdminService {

    // Methods regarding the user groups
    UserGroup createUserGroup(UserGroupRequest request);
    UserGroup updateUserGroup(String id, UserGroupRequest request);
    UserGroup deleteUserGroup(String id);

}
