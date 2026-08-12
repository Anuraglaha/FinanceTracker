package com.anurag.financetracker.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.anurag.financetracker.dto.AddBudgetRequest;
import com.anurag.financetracker.dto.ApiResponse;
import com.anurag.financetracker.dto.BudgetResponse;
import com.anurag.financetracker.dto.UpdateBudgetRequest;
import com.anurag.financetracker.service.BudgetService;

import jakarta.validation.Valid;


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
    
    @GetMapping("/{id}")
    public ApiResponse<BudgetResponse> getBudgetById(
            @PathVariable Integer id) {

        return budgetService.getBudgetById(id);
    }

    @PutMapping("/{id}")
    public ApiResponse<BudgetResponse> updateBudget(
            @PathVariable Integer id,
            @Valid @RequestBody UpdateBudgetRequest request) {

        return budgetService.updateBudget(id, request);
    }
    
    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteBudget(@PathVariable Integer id) {

        return budgetService.deleteBudget(id);
    }
    
}