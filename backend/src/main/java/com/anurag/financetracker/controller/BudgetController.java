package com.anurag.financetracker.controller;

import com.anurag.financetracker.dto.AddBudgetRequest;
import com.anurag.financetracker.dto.ApiResponse;
import com.anurag.financetracker.dto.BudgetResponse;
import com.anurag.financetracker.service.BudgetService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/budgets")
public class BudgetController {

    private final BudgetService budgetService;

    public BudgetController(BudgetService budgetService) {
        this.budgetService = budgetService;
    }

    @PostMapping
    public ApiResponse<BudgetResponse> addBudget(
            @Valid @RequestBody AddBudgetRequest request) {

        return budgetService.addBudget(request);
    }

    @GetMapping
    public ApiResponse<List<BudgetResponse>> getAllBudgets() {
        return budgetService.getAllBudgets();
    }
    
}