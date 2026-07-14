package com.anurag.financetracker.controller;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.anurag.financetracker.dto.ApiResponse;
import com.anurag.financetracker.dto.UserResponse;
import com.anurag.financetracker.entity.User;
import com.anurag.financetracker.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/register")
    public ApiResponse<UserResponse> registerUser(
            @Valid @RequestBody User user){

        User savedUser = userService.registerUser(user);

        UserResponse response = new UserResponse(savedUser);

        return new ApiResponse<>(
                true,
                "User registered successfully",
                response
        );

    }

    @GetMapping
    public ApiResponse<List<UserResponse>> getAllUsers(){

        List<User> users = userService.getAllUsers();

        List<UserResponse> response = users.stream()
                .map(UserResponse::new)
                .toList();

        return new ApiResponse<>(
                true,
                "Users fetched successfully",
                response
        );

    }

    @GetMapping("/{id}")
    public ApiResponse<UserResponse> getUserById(@PathVariable int id){

        User user = userService.getUserById(id);

        UserResponse response = new UserResponse(user);

        return new ApiResponse<>(
                true,
                "User fetched successfully",
                response
        );

    }

    @PutMapping("/{id}")
    public ApiResponse<UserResponse> updateUser(
            @PathVariable int id,
            @Valid @RequestBody User user){

        User updatedUser = userService.updateUser(id, user);

        UserResponse response = new UserResponse(updatedUser);

        return new ApiResponse<>(
                true,
                "User updated successfully",
                response
        );

    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteUser(
            @PathVariable int id){

        userService.deleteUser(id);

        return new ApiResponse<>(
                true,
                "User deleted successfully",
                null
        );

    }

}