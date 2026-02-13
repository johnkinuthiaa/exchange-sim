package com.slippery.userservice.controller;

import com.slippery.userservice.dto.PasswordChangeReq;
import com.slippery.userservice.dto.UserCreationRequest;
import com.slippery.userservice.dto.UserResponse;
import com.slippery.userservice.service.UserServices;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserServices service;

    public UserController(UserServices service) {
        this.service = service;
    }
    @PostMapping
    public ResponseEntity<UserResponse> createNewUser(@RequestBody UserCreationRequest request) {
        UserResponse createdUser =service.createNewUser(request);
        return ResponseEntity.status(HttpStatusCode.valueOf(createdUser.getStatusCode())).body(createdUser);
    }
    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable String userId) {
        UserResponse foundUser =service.getUserById(userId);
        return ResponseEntity.status(HttpStatusCode.valueOf(foundUser.getStatusCode())).body(foundUser);
    }
    @DeleteMapping("/{userId}")
    public ResponseEntity<UserResponse> deleteUserById(@PathVariable String userId) {
        UserResponse deletedUser =service.deleteUserById(userId);
        return ResponseEntity.status(HttpStatusCode.valueOf(deletedUser.getStatusCode())).body(deletedUser);
    }
    @PatchMapping("/{userId}")
    public ResponseEntity<UserResponse> updateUser(
            @PathVariable String userId,
            @RequestBody UserCreationRequest request)
    {
        UserResponse updatedUser =service.updateUser(userId, request);

        return ResponseEntity.status(HttpStatusCode.valueOf(updatedUser.getStatusCode())).body(updatedUser);
    }
    @GetMapping
    public ResponseEntity<UserResponse> getAllUsers() {
        UserResponse allUsers =service.getAllUsers();
        return ResponseEntity.status(HttpStatusCode.valueOf(allUsers.getStatusCode())).body(allUsers);
    }
    @PatchMapping("/{userId}/change-pass")
    public ResponseEntity<UserResponse> changePassword(
            @PathVariable String userId,
            @RequestBody PasswordChangeReq passwordRequest) {
        UserResponse changedPassword =service.changePassword(userId, passwordRequest);
        return ResponseEntity.status(HttpStatusCode.valueOf(changedPassword.getStatusCode())).body(changedPassword);
    }
}
