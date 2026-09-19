package com.anurag.financetracker.dto;

import lombok.Data;

@Data
public class DashboardResponse {

    private Double totalIncome;
    private Double totalExpense;
    private Double balance;
    private Double totalBudget;
    private Integer budgetCount;
}