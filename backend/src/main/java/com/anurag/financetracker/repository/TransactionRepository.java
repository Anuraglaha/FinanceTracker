package com.anurag.financetracker.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.anurag.financetracker.entity.Transaction;
import com.anurag.financetracker.entity.User;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

    List<Transaction> findByUser(User user);

}