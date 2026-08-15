package com.anurag.financetracker.dto;

import java.time.LocalDate;

import com.anurag.financetracker.enums.Category;

import lombok.Data;

@Data
public class BudgetResponse {

    private Integer id;
    private LocalDate budgetMonth;
    private Category category;
    private Double budgetAmount;
}