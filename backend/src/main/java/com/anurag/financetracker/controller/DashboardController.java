package com.anurag.financetracker.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.anurag.financetracker.dto.CategoryExpenseResponse;
import com.anurag.financetracker.dto.DashboardResponse;
import com.anurag.financetracker.dto.MonthlyReportResponse;
import com.anurag.financetracker.service.DashboardService;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping
    public DashboardResponse getDashboard() {
        return dashboardService.getDashboard();
    }

    @GetMapping("/category-expenses")
    public List<CategoryExpenseResponse> getCategoryWiseExpenses() {
        return dashboardService.getCategoryWiseExpenses();
    }

    @GetMapping("/monthly")
    public List<MonthlyReportResponse> getMonthlyReport() {
        return dashboardService.getMonthlyReport();
    }
}