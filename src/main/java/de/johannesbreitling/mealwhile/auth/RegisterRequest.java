package de.johannesbreitling.mealwhile.auth;

import de.johannesbreitling.mealwhile.business.model.user.UserGroup;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {

    private String username;

    private String password;

    private String firstname;

    private String lastname;

    // TODO implement user group
    //private String userGroupId;
}
