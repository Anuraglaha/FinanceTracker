package com.anurag.financetracker.service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.anurag.financetracker.dto.LoginRequest;
import com.anurag.financetracker.entity.User;
import com.anurag.financetracker.enums.Role;
import com.anurag.financetracker.repository.UserRepository;
import com.anurag.financetracker.security.JwtService;
@Service
public class UserService {

    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public User registerUser(User user){
        if(userRepository.existsByEmail(user.getEmail())){
            throw new RuntimeException("Email already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.USER);
        return userRepository.save(user);
    }

    public String login(LoginRequest request){
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));
        if(!passwordEncoder.matches(
                request.getPassword(),
                user.getPassword())){
            throw new RuntimeException("Invalid email or password");
        }
        return jwtService.generateToken(user.getEmail());
}

    public List<User> getAllUsers(){

        return userRepository.findAll();

    }

    public User getUserById(int id){

        User user = userRepository.findById(id).orElse(null);

        if(user == null){

            throw new RuntimeException("User not found");

        }

        return user;

    }

    public User updateUser(int id, User updatedUser){

        User existingUser = userRepository.findById(id).orElse(null);

        if(existingUser == null){
            throw new RuntimeException("User not found");
        }

        existingUser.setName(updatedUser.getName());
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setPassword(updatedUser.getPassword());

        return userRepository.save(existingUser);

    }

    public void deleteUser(int id){

        User existingUser = userRepository.findById(id).orElse(null);

        if(existingUser == null){
            throw new RuntimeException("User not found");
        }

        userRepository.delete(existingUser);

    }

}