package de.johannesbreitling.mealwhile.business.model.responses.admin;

import de.johannesbreitling.mealwhile.business.model.responses.IApiResponse;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserGroupResponse implements IApiResponse {

    private final String id;
    private final String name;
    private final String color;

}
