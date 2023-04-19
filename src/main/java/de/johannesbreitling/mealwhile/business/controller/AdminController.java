package de.johannesbreitling.mealwhile.business.controller;

import de.johannesbreitling.mealwhile.business.model.exceptions.EntityAlreadyExistsException;
import de.johannesbreitling.mealwhile.business.model.requests.admin.UserGroupRequest;
import de.johannesbreitling.mealwhile.business.model.responses.admin.ApiError;
import de.johannesbreitling.mealwhile.business.model.responses.admin.ApiResponse;
import de.johannesbreitling.mealwhile.business.model.responses.admin.UserGroupResponse;
import de.johannesbreitling.mealwhile.business.model.user.UserGroup;
import de.johannesbreitling.mealwhile.business.services.interfaces.IAdminService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {

    private final IAdminService adminService;

    public AdminController(IAdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/test")
    public ResponseEntity<String> testAdmin() {
        return ResponseEntity.ok("HALLO VON ADMIN!");
    }

    @PostMapping("/group")
    public ResponseEntity<ApiResponse> createUserGroup(@RequestBody UserGroupRequest group) {

        if (group.name() == null || group.color() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiError("400", "Missing arguments for creation of a user group"));
        }

        try {
            UserGroup createdGroup = adminService.createUserGroup(group);

            return ResponseEntity.ok(new UserGroupResponse(createdGroup.getId()));
        } catch (EntityAlreadyExistsException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ApiError("400", "The role already exists"));
        }
    }

}
