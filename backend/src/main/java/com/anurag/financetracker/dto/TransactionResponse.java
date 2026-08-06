package com.anurag.financetracker.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.anurag.financetracker.enums.TransactionType;

import lombok.Data;

@Data
public class TransactionResponse {

    private Integer id;
    private Double amount;
    private TransactionType type;
    private String category;
    private String description;
    private LocalDate transactionDate;
    private LocalDateTime createdAt;

}