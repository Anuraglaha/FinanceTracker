package com.anurag.financetracker.dto;

import java.time.LocalDate;

import com.anurag.financetracker.enums.Category;
import com.anurag.financetracker.enums.TransactionType;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class AddTransactionRequest {

    //@NotNull
    //private Integer userId;
    //no longer needed because authoprization is done using JWT and the user is fetched from the security context
    
    @NotNull
    @Positive
    private Double amount;

    @NotNull
    private TransactionType type;

    @NotNull
    private Category category;

    private String description;

    @NotNull
    @PastOrPresent
    private LocalDate transactionDate;
}