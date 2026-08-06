package com.anurag.financetracker.dto;

import java.time.LocalDate;

import com.anurag.financetracker.enums.TransactionType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class AddTransactionRequest {

    @NotNull
    private Integer userId;

    @NotNull
    @Positive
    private Double amount;

    @NotNull
    private TransactionType type;

    @NotBlank
    private String category;

    private String description;

    @NotNull
    @PastOrPresent
    private LocalDate transactionDate;
}