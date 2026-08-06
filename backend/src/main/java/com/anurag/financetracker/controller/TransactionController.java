package com.anurag.financetracker.controller;

import java.util.List;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.anurag.financetracker.dto.AddTransactionRequest;
import com.anurag.financetracker.dto.ApiResponse;
import com.anurag.financetracker.dto.TransactionResponse;
import com.anurag.financetracker.service.TransactionService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/transactions")
@Validated
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    public ApiResponse<TransactionResponse> addTransaction(
            @Valid @RequestBody AddTransactionRequest request) {

        return transactionService.addTransaction(request);
    }

    @GetMapping
    public ApiResponse<List<TransactionResponse>> getAllTransactions() {
        return transactionService.getAllTransactions();
    }

    @GetMapping("/{id}")
    public ApiResponse<TransactionResponse> getTransactionById(
            @PathVariable Integer id) {

        return transactionService.getTransactionById(id);
    }
    
}