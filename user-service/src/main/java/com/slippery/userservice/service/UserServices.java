package com.slippery.userservice.service;

import com.slippery.userservice.dto.PasswordChangeReq;
import com.slippery.userservice.dto.UserCreationRequest;
import com.slippery.userservice.dto.UserResponse;

public interface UserServices {
    UserResponse createNewUser(UserCreationRequest request);
    UserResponse getUserById(String userId);
    UserResponse deleteUserById(String userId);
    UserResponse updateUser(String userId,UserCreationRequest request);
    UserResponse getAllUsers();
    UserResponse changePassword(String userId, PasswordChangeReq passwordRequest);
}
