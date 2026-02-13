package com.slippery.userservice.service.impl;

import com.slippery.userservice.dto.PasswordChangeReq;
import com.slippery.userservice.dto.UserCreationRequest;
import com.slippery.userservice.dto.UserDto;
import com.slippery.userservice.dto.UserResponse;
import com.slippery.userservice.models.User;
import com.slippery.userservice.repository.UserRepository;
import com.slippery.userservice.service.UserServices;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserServices {
    private final PasswordEncoder passwordEncoder =new BCryptPasswordEncoder(12);
    private final UserRepository repository;
    private final ModelMapper modelMapper =new ModelMapper();

    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserResponse createNewUser(UserCreationRequest request) {
        UserResponse response =new UserResponse();
        if(checkIfEmailExists(request.getEmail())){
            response.setMessage("User with the email already exists");
            response.setStatusCode(409);
            return response;
        }
        if(checkIfUserNameExists(request.getUsername())){
            response.setMessage("User with the username already exists");
            response.setStatusCode(409);
            return response;
        }
        User user =modelMapper.map(request, User.class);
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        repository.save(user);
//        create an account for the user
        response.setMessage("User "+user.getUsername()+" created successfully");
        response.setStatusCode(201);
        response.setUser(modelMapper.map(user, UserDto.class));
        return response;
    }

    @Override
    public UserResponse getUserById(String userId) {
        UserResponse response =new UserResponse();
        var user =repository.findById(userId);
        if(user.isEmpty()){
            response.setMessage("User with id "+userId+" not found");
            response.setStatusCode(404);
            return response;
        }
        response.setMessage("User with id "+userId+"found ");
        response.setStatusCode(200);
        response.setUser(modelMapper.map(user, UserDto.class));

        return response;
    }

    @Override
    public UserResponse deleteUserById(String userId) {
        UserResponse response =new UserResponse();
        UserResponse existingUser =getUserById(userId);
        if(existingUser.getStatusCode() !=200){
            return existingUser;
        }
        repository.deleteById(userId);
//        delete user info from other services
        response.setMessage("User with id "+userId+" deleted successfully");
        response.setStatusCode(204);
        return response;
    }

    @Override
    public UserResponse updateUser(String userId, UserCreationRequest request) {

        return null;
    }

    @Override
    public UserResponse getAllUsers() {
        UserResponse response =new UserResponse();
        List<User> allUsers =repository.findAll();
        if(allUsers.isEmpty()){
            response.setMessage("No user found in the database ");
            response.setStatusCode(200);
            return response;
        }
        response.setMessage("all users");
        response.setStatusCode(200);
        List<UserDto> users = List.of(modelMapper.map(allUsers, UserDto[].class));
        response.setUsers(users);
        return response;
    }

    @Override
    public UserResponse changePassword(String userId, PasswordChangeReq passwordRequest) {
        UserResponse response =new UserResponse();
        var user =repository.findById(userId);
        if(user.isEmpty()){
            response.setMessage("User with id "+userId+" not found");
            response.setStatusCode(404);
            return response;
        }
        if(!passwordEncoder.matches(passwordRequest.getOldPassword(),user.get().getPasswordHash())){
            response.setMessage("Old password does not match with provided password");
            response.setStatusCode(404);
            return response;
        }
        user.get().setPasswordHash(passwordEncoder.encode(passwordRequest.getNewPassword()));
        repository.save(user.get());
        response.setMessage("User password updated successfully");
        response.setStatusCode(200);

        return response;
    }
    private boolean checkIfUserNameExists(String username){
        Optional<User> foundUser =repository.findUserByUsername(username);
        return foundUser.isPresent();
    }
    private boolean checkIfEmailExists(String email){
        Optional<User> foundUser =repository.findUserByEmail(email);
        return foundUser.isPresent();
    }

}
