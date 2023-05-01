package de.johannesbreitling.mealwhile.business.model.responses.admin;

import de.johannesbreitling.mealwhile.business.model.responses.IApiResponse;
import de.johannesbreitling.mealwhile.business.model.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Response for a group of users
 */
@Data
public class UserCollectionResponse implements IApiResponse {

    private final List<UserResponse> users;

    public UserCollectionResponse(List<User> users) {

        this.users = users
                .stream()
                .map(
                        user -> new UserResponse(
                                    user.getId(),
                                    user.getUsername(),
                                    new UserGroupResponse(
                                            user.getGroup().getId(),
                                            user.getGroup().getName(),
                                            user.getGroup().getColor()
                                    )
                                )
                )
                .collect(Collectors.toList());
    }

}
