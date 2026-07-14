package com.anurag.financetracker.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.anurag.financetracker.entity.User;
import com.anurag.financetracker.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public User registerUser(User user){
        if(userRepository.existsByEmail(user.getEmail())){
            throw new RuntimeException("Email already exists");
        }
        return userRepository.save(user);
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