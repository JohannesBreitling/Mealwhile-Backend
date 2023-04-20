package de.johannesbreitling.mealwhile.business.model.requests.admin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class UserGroupRequest {

    private String color;
    private String name;

}
