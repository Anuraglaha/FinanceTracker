package com.anurag.financetracker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MonthlyReportResponse {

    private String month;
    private Double income;
    private Double expense;
}