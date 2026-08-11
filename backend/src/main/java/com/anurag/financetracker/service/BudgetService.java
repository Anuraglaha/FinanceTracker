package com.anurag.financetracker.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.anurag.financetracker.dto.AddBudgetRequest;
import com.anurag.financetracker.dto.ApiResponse;
import com.anurag.financetracker.dto.BudgetResponse;
import com.anurag.financetracker.dto.UpdateBudgetRequest;
import com.anurag.financetracker.entity.Budget;
import com.anurag.financetracker.entity.User;
import com.anurag.financetracker.repository.BudgetRepository;
import com.anurag.financetracker.repository.UserRepository;

@Service
public class BudgetService {

    private final BudgetRepository budgetRepository;
    private final UserRepository userRepository;

    public BudgetService(BudgetRepository budgetRepository, UserRepository userRepository) {
        this.budgetRepository = budgetRepository;
        this.userRepository = userRepository;
    }


    public ApiResponse<BudgetResponse> addBudget(AddBudgetRequest request) {
        User user = userRepository.findById(request.getUserId()).orElseThrow(() -> new RuntimeException("User not found"));
        
        
        boolean exists = budgetRepository.existsByUserAndBudgetTypeAndBudgetMonth(
                user,
                request.getBudgetType(),
                request.getBudgetMonth()
        );
        
        if (exists) {
            throw new RuntimeException(
                    "Budget already exists for this user, type and month"
            );
        }

        
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

    public ApiResponse<BudgetResponse> updateBudget(
        Integer id,
        UpdateBudgetRequest request) {

        Budget budget = budgetRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Budget not found"));

        boolean exists = budgetRepository.existsByUserAndBudgetTypeAndBudgetMonthAndIdNot(
                budget.getUser(),
                request.getBudgetType(),
                request.getBudgetMonth(),
                id
        );

        if (exists) {
            throw new RuntimeException(
                    "Another budget already exists for this user, type and month"
            );
        }

        budget.setBudgetMonth(request.getBudgetMonth());
        budget.setBudgetType(request.getBudgetType());
        budget.setBudgetAmount(request.getBudgetAmount());

        Budget updatedBudget = budgetRepository.save(budget);

        BudgetResponse response = new BudgetResponse();

        response.setId(updatedBudget.getId());
        response.setBudgetMonth(updatedBudget.getBudgetMonth());
        response.setBudgetType(updatedBudget.getBudgetType());
        response.setBudgetAmount(updatedBudget.getBudgetAmount());

        return new ApiResponse<>(
                true,
                "Budget updated successfully",
                response
        );
    }

    public ApiResponse<List<BudgetResponse>> getAllBudgets() {

        List<Budget> budgets = budgetRepository.findAll();

        List<BudgetResponse> responses = new ArrayList<>();

        for (Budget budget : budgets) {

            BudgetResponse response = new BudgetResponse();

            response.setId(budget.getId());
            response.setBudgetMonth(budget.getBudgetMonth());
            response.setBudgetType(budget.getBudgetType());
            response.setBudgetAmount(budget.getBudgetAmount());

            responses.add(response);
        }

        return new ApiResponse<>(
                true,
                "Budgets fetched successfully",
                responses
        );
    }


    public ApiResponse<BudgetResponse> getBudgetById(Integer id) {

        Budget budget = budgetRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Budget not found"));

        BudgetResponse response = new BudgetResponse();

        response.setId(budget.getId());
        response.setBudgetMonth(budget.getBudgetMonth());
        response.setBudgetType(budget.getBudgetType());
        response.setBudgetAmount(budget.getBudgetAmount());

        return new ApiResponse<>(
                true,
                "Budget fetched successfully",
                response
        );
    }
}
