package de.johannesbreitling.mealwhile.business.model.requests.admin;

import lombok.Data;

@Data
public class UserRequest {

    private String username;
    private String password;
    private String firstname;
    private String lastname;
    private String groupId;

}
