package de.johannesbreitling.mealwhile.business.model.responses.admin;

import de.johannesbreitling.mealwhile.business.model.responses.IApiResponse;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class UserGroupCollectionResponse implements IApiResponse {
    private final List<UserGroupResponse> groups;
}
