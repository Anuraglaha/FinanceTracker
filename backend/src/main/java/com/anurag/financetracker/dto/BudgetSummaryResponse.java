package com.anurag.financetracker.dto;

import lombok.Data;

@Data
public class BudgetSummaryResponse {

    private Double budgetAmount;
    private Double spentAmount;
    private Double remainingAmount;
}