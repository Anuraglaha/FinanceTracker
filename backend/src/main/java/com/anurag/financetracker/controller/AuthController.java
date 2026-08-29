package com.anurag.financetracker.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.anurag.financetracker.dto.ApiResponse;
import com.anurag.financetracker.dto.LoginRequest;
import com.anurag.financetracker.dto.LoginResponse;
import com.anurag.financetracker.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ApiResponse<LoginResponse> login(
            @Valid @RequestBody LoginRequest request) {

        String token = userService.login(request);

        LoginResponse response = new LoginResponse(token);

        return new ApiResponse<>(
                true,
                "Login successful",
                response
        );
    }
}