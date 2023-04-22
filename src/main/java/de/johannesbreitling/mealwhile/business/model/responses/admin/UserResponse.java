package de.johannesbreitling.mealwhile.business.model.responses.admin;

import de.johannesbreitling.mealwhile.business.model.responses.IApiResponse;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserResponse implements IApiResponse {

    private final String userId;
    private final String username;
    private final UserGroupResponse userGroup;

}
