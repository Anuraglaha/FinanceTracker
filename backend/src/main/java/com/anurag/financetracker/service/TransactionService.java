package com.anurag.financetracker.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    public ApiResponse<List<TransactionResponse>> getAllTransactions() {

        List<Transaction> transactions = transactionRepository.findAll();

        List<TransactionResponse> responseList = new ArrayList<>();

        for (Transaction transaction : transactions) {

            TransactionResponse response = new TransactionResponse();

            response.setId(transaction.getId());
            response.setAmount(transaction.getAmount());
            response.setType(transaction.getType());
            response.setCategory(transaction.getCategory());
            response.setDescription(transaction.getDescription());
            response.setTransactionDate(transaction.getTransactionDate());
            response.setCreatedAt(transaction.getCreatedAt());

            responseList.add(response);
        }

        return new ApiResponse<>(
                true,
                "Transactions fetched successfully",
                responseList
        );
    }

    public ApiResponse<TransactionResponse> getTransactionById(Integer id) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));

        TransactionResponse response = new TransactionResponse();

        response.setId(transaction.getId());
        response.setAmount(transaction.getAmount());
        response.setType(transaction.getType());
        response.setCategory(transaction.getCategory());
        response.setDescription(transaction.getDescription());
        response.setTransactionDate(transaction.getTransactionDate());
        response.setCreatedAt(transaction.getCreatedAt());

        return new ApiResponse<>(
                true,
                "Transaction fetched successfully",
                response
        );
    }

    public ApiResponse<List<TransactionResponse>> getTransactionsBetweenDates(
        LocalDate from,
        LocalDate to) {
        
        List<Transaction> transactions = transactionRepository.findByTransactionDateBetween(from, to);
        
        List<TransactionResponse> responseList = new ArrayList<>();

        for (Transaction transaction : transactions) {

            TransactionResponse response = new TransactionResponse();

            response.setId(transaction.getId());
            response.setAmount(transaction.getAmount());
            response.setType(transaction.getType());
            response.setCategory(transaction.getCategory());
            response.setDescription(transaction.getDescription());
            response.setTransactionDate(transaction.getTransactionDate());
            response.setCreatedAt(transaction.getCreatedAt());

            responseList.add(response);
        }

        return new ApiResponse<>(
                true,
                "Transactions fetched successfully",
                responseList
        );
    }
}