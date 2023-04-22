package de.johannesbreitling.mealwhile.business.controller;

import de.johannesbreitling.mealwhile.business.model.exceptions.EntityAlreadyExistsException;
import de.johannesbreitling.mealwhile.business.model.exceptions.EntityNotFoundException;
import de.johannesbreitling.mealwhile.business.model.exceptions.UserGroupNotEmptyException;
import de.johannesbreitling.mealwhile.business.model.requests.admin.UserGroupRequest;
import de.johannesbreitling.mealwhile.business.model.requests.admin.UserRequest;
import de.johannesbreitling.mealwhile.business.model.responses.ApiError;
import de.johannesbreitling.mealwhile.business.model.responses.IApiResponse;
import de.johannesbreitling.mealwhile.business.model.responses.admin.*;
import de.johannesbreitling.mealwhile.business.model.user.UserGroup;
import de.johannesbreitling.mealwhile.business.services.interfaces.IAdminService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {

    private final IAdminService adminService;

    public AdminController(IAdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/groups")
    public ResponseEntity<IApiResponse> getAllUserGroups() {

        var foundGroups = adminService.getAllUserGroups();

        UserGroupCollectionResponse response = new UserGroupCollectionResponse((
                foundGroups
                        .stream()
                        .map(
                            group -> new UserGroupResponse(group.getId(), group.getName(), group.getColor()))
                                                .collect(Collectors.toList()
                        )
        ));

        return ResponseEntity.ok(response);
    }

    @PostMapping("/group")
    public ResponseEntity<IApiResponse> createUserGroup(@RequestBody UserGroupRequest group) {

        if (group == null || group.getName() == null || group.getColor() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiError("400", "Missing arguments for creation of a user group"));
        }

        try {
            UserGroup createdGroup = adminService.createUserGroup(group);

            return ResponseEntity.ok(new EntityResponse(createdGroup.getId()));
        } catch (EntityAlreadyExistsException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ApiError("400", "The user group already exists"));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiError("500", "Something went wrong."));
        }
    }

    @PatchMapping("/group/{groupId}")
    public ResponseEntity<IApiResponse> updateUserGroup(
            @PathVariable String groupId,
            @RequestBody UserGroupRequest request
            ) {

        if (groupId == null) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ApiError("400", "Missing userGroupId."));
        }

        if (request == null || (request.getName() == null && request.getColor() == null)) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ApiError("400", "Arguments for updating a user group cannot be empty."));
        }

        try {
            UserGroup updatedGroup = adminService.updateUserGroup(groupId, request);
            return ResponseEntity.ok(new EntityResponse(updatedGroup.getId()));
        } catch (EntityNotFoundException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new ApiError("404", "User group not found."));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiError("500", "Something went wrong."));
        }
    }

    @DeleteMapping("/group/{groupId}")
    public ResponseEntity<IApiResponse> deleteUserGroup(
            @PathVariable String groupId
    ) {

        if (groupId == null) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ApiError("400", "Missing userGroupId."));
        }

        try {
            UserGroup updatedGroup = adminService.deleteUserGroup(groupId);
            return ResponseEntity.ok(new EntityResponse(updatedGroup.getId()));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiError("404", "User group not found."));
        } catch (UserGroupNotEmptyException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiError("400", "User group is not empty."));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiError("500", "Something went wrong."));
        }
    }

    @GetMapping("/users")
    public ResponseEntity<IApiResponse> getAllUsers() {
        var users = adminService.getAllUsers();

        if (users.isEmpty()) {
            // Return empty list
            return ResponseEntity.ok(new UserCollectionResponse(new ArrayList<>()));
        }

        var response = new UserCollectionResponse(
            users
                    .stream()
                    .map(user -> new UserResponse(
                            user.getId(),
                            user.getUsername(),
                            new UserGroupResponse(user.getGroup().getId(), user.getGroup().getName(), user.getGroup().getColor())
                    ))
                    .collect(Collectors.toList())
        );

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/user/{userId}")
    public ResponseEntity<IApiResponse> updateUser(
            @PathVariable String userId,
            @RequestBody UserRequest request
            ) {

        if (userId == null) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ApiError("400", "No id for the user is provided."));
        }

        if (request == null
                || (request.getFirstname() == null && request.getLastname() == null
                && request.getPassword() == null && request.getGroupId() == null)
        ) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ApiError("400", "Arguments for updating a user cannot be empty."));
        }

        try {
            var updatedUser = adminService.updateUser(userId, request);
            return ResponseEntity.ok(new EntityResponse(updatedUser.getId()));
        } catch (EntityNotFoundException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new ApiError("404", "The given " + e.getEntity() + " was not found."));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiError("500", "Something went wrong."));
        }
    }

    @DeleteMapping("/user/{userId}")
    public ResponseEntity<IApiResponse> deleteUser(@PathVariable String userId) {

        if (userId == null) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ApiError("400", "No id for the user provided."));
        }

        try {
            var deletedUser = adminService.deleteUser(userId);
            return ResponseEntity.ok(new EntityResponse(deletedUser.getId()));
        } catch (EntityNotFoundException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new ApiError("404", "The given user was not found."));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiError("500", "Something went wrong"));
        }
    }

}
