package com.anurag.financetracker.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.anurag.financetracker.dto.AddTransactionRequest;
import com.anurag.financetracker.dto.ApiResponse;
import com.anurag.financetracker.dto.TransactionResponse;
import com.anurag.financetracker.entity.Transaction;
import com.anurag.financetracker.entity.User;
import com.anurag.financetracker.repository.TransactionRepository;
import com.anurag.financetracker.repository.UserRepository;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;

    public TransactionService(TransactionRepository transactionRepository,
                              UserRepository userRepository) {
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
    }

    public ApiResponse<TransactionResponse> addTransaction(AddTransactionRequest request) {

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Transaction transaction = new Transaction();

        transaction.setAmount(request.getAmount());
        transaction.setType(request.getType());
        transaction.setCategory(request.getCategory());
        transaction.setDescription(request.getDescription());
        transaction.setTransactionDate(request.getTransactionDate());

        transaction.setCreatedAt(LocalDateTime.now());
        transaction.setUser(user);

        Transaction savedTransaction = transactionRepository.save(transaction);

        TransactionResponse response = new TransactionResponse();

        response.setId(savedTransaction.getId());
        response.setAmount(savedTransaction.getAmount());
        response.setType(savedTransaction.getType());
        response.setCategory(savedTransaction.getCategory());
        response.setDescription(savedTransaction.getDescription());
        response.setTransactionDate(savedTransaction.getTransactionDate());
        response.setCreatedAt(savedTransaction.getCreatedAt());

        return new ApiResponse<>(
                true,
                "Transaction added successfully",
                response
        );
    }
}