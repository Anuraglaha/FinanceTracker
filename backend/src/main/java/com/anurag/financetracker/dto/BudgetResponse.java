package com.anurag.financetracker.dto;

import com.anurag.financetracker.enums.BudgetType;
import lombok.Data;

import java.time.LocalDate;

@Data
public class BudgetResponse {

    private Integer id;
    private LocalDate budgetMonth;
    private BudgetType budgetType;
    private Double budgetAmount;
}