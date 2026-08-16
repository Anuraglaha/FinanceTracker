package com.anurag.financetracker.dto;

import lombok.Data;

@Data
public class MonthlySummaryResponse {

    private Double totalIncome;
    private Double totalExpense;
    private Double balance;
}