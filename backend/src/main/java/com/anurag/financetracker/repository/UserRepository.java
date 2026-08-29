package com.anurag.financetracker.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.anurag.financetracker.entity.User;

public interface UserRepository extends JpaRepository<User, Integer>{

    boolean existsByEmail(String email);
    Optional<User> findByEmail(String email);

}