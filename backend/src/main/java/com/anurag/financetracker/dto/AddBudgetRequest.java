package com.anurag.financetracker.dto;

import java.time.LocalDate;

import com.anurag.financetracker.enums.Category;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class AddBudgetRequest {

    @NotNull
    private Integer userId;

    @NotNull
    private LocalDate budgetMonth;

    @NotNull
    private Category category;

    @NotNull
    @Positive
    private Double budgetAmount;
}