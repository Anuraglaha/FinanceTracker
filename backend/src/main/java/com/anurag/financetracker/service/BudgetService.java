package com.anurag.financetracker.service;

import com.anurag.financetracker.dto.AddBudgetRequest;
import com.anurag.financetracker.dto.ApiResponse;
import com.anurag.financetracker.dto.BudgetResponse;
import com.anurag.financetracker.entity.Budget;
import com.anurag.financetracker.entity.User;
import com.anurag.financetracker.repository.BudgetRepository;
import com.anurag.financetracker.repository.UserRepository;

public class BudgetService {

    private final BudgetRepository budgetRepository;
    private final UserRepository userRepository;

    public BudgetService(BudgetRepository budgetRepository, UserRepository userRepository) {
        this.budgetRepository = budgetRepository;
        this.userRepository = userRepository;
    }


    public ApiResponse<BudgetResponse> addBudget(AddBudgetRequest request) {
        User user = userRepository.findById(request.getUserId()).orElseThrow(() -> new RuntimeException("User not found"));
        Budget budget = new Budget();
        budget.setUser(user);
        budget.setBudgetMonth(request.getBudgetMonth());
        budget.setBudgetType(request.getBudgetType());
        budget.setBudgetAmount(request.getBudgetAmount());

        Budget savedBudget = budgetRepository.save(budget);

        BudgetResponse response = new BudgetResponse();

        response.setId(savedBudget.getId());
        response.setBudgetMonth(savedBudget.getBudgetMonth());
        response.setBudgetType(savedBudget.getBudgetType());
        response.setBudgetAmount(savedBudget.getBudgetAmount());

        return new ApiResponse<>(
                true,
                "Budget added successfully",
                response
        );

    }
}
