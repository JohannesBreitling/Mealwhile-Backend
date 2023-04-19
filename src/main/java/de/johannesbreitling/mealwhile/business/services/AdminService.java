package de.johannesbreitling.mealwhile.business.services;

import de.johannesbreitling.mealwhile.business.model.exceptions.EntityAlreadyExistsException;
import de.johannesbreitling.mealwhile.business.model.requests.admin.UserGroupRequest;
import de.johannesbreitling.mealwhile.business.model.user.UserGroup;
import de.johannesbreitling.mealwhile.business.repositories.UserGroupRepository;
import de.johannesbreitling.mealwhile.business.services.interfaces.IAdminService;
import org.springframework.stereotype.Service;

@Service
public class AdminService implements IAdminService {

    private final UserGroupRepository userGroupRepository;

    public AdminService(UserGroupRepository userGroupRepository) {
        this.userGroupRepository = userGroupRepository;
    }

    @Override
    public UserGroup createUserGroup(UserGroupRequest request) {

        // Check if the group already exists
        var foundGroup = userGroupRepository.findGroupByName(request.name());

        if (foundGroup.isPresent()) {
            throw new EntityAlreadyExistsException();
        }

        // Group does not exist
        UserGroup group = UserGroup.builder().color(request.color()).name(request.name()).build();

        userGroupRepository.save(group);

        return group;
    }

}
