package de.johannesbreitling.mealwhile.business.model.responses.admin;

import de.johannesbreitling.mealwhile.business.model.responses.IApiResponse;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * Response for a group of users
 */
@Data
@AllArgsConstructor
public class UserCollectionResponse implements IApiResponse {

    private final List<UserResponse> users;

}
