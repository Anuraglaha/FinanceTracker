package com.anurag.financetracker.dto;

import java.time.LocalDate;

import com.anurag.financetracker.enums.BudgetType;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class UpdateBudgetRequest {

    @NotNull
    private LocalDate budgetMonth;

    @NotNull
    private BudgetType budgetType;

    @NotNull
    @Positive
    private Double budgetAmount;
}