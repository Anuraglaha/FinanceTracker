package com.anurag.financetracker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BudgetVsActualResponse {

    private String category;
    private Double budget;
    private Double spent;
    private Double remaining;
    private Double utilizationPercentage;

}