package de.johannesbreitling.mealwhile.business.controller;

import de.johannesbreitling.mealwhile.business.model.exceptions.EntityAlreadyExistsException;
import de.johannesbreitling.mealwhile.business.model.exceptions.EntityNotFoundException;
import de.johannesbreitling.mealwhile.business.model.exceptions.UserGroupNotEmptyException;
import de.johannesbreitling.mealwhile.business.model.requests.admin.UserGroupRequest;
import de.johannesbreitling.mealwhile.business.model.responses.ApiError;
import de.johannesbreitling.mealwhile.business.model.responses.IApiResponse;
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
    public ResponseEntity<IApiResponse> createUserGroup(@RequestBody UserGroupRequest group) {

        if (group.getName() == null || group.getColor() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiError("400", "Missing arguments for creation of a user group"));
        }

        try {
            UserGroup createdGroup = adminService.createUserGroup(group);

            return ResponseEntity.ok(new UserGroupResponse(createdGroup.getId()));
        } catch (EntityAlreadyExistsException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ApiError("400", "The user group already exists"));
        }
    }

    @PatchMapping("/group/{groupId}")
    public ResponseEntity<IApiResponse> updateUserGroup(
            @PathVariable String groupId,
            @RequestBody UserGroupRequest request
            ) {

        if (request.getName() == null && request.getColor() == null) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ApiError("400", "Arguments for updating a user group cannot be empty."));
        }

        try {
            UserGroup updatedGroup = adminService.updateUserGroup(groupId, request);
            return ResponseEntity.ok(new UserGroupResponse(updatedGroup.getId()));
        } catch (EntityNotFoundException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new ApiError("404", "User group not found."));
        }
    }

    @DeleteMapping("/group/{groupId}")
    public ResponseEntity<IApiResponse> deleteUserGroup(
            @PathVariable String groupId
    ) {
        try {
            UserGroup updatedGroup = adminService.deleteUserGroup(groupId);
            return ResponseEntity.ok(new UserGroupResponse(updatedGroup.getId()));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiError("404", "User group not found."));
        } catch (UserGroupNotEmptyException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiError("400", "User group is not empty."));
        }
    }

}
