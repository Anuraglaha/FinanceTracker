package com.anurag.financetracker.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.anurag.financetracker.dto.AddBudgetRequest;
import com.anurag.financetracker.dto.ApiResponse;
import com.anurag.financetracker.dto.BudgetResponse;
import com.anurag.financetracker.dto.BudgetSummaryResponse;
import com.anurag.financetracker.dto.UpdateBudgetRequest;
import com.anurag.financetracker.entity.Budget;
import com.anurag.financetracker.entity.User;
import com.anurag.financetracker.enums.TransactionType;
import com.anurag.financetracker.repository.BudgetRepository;
import com.anurag.financetracker.repository.TransactionRepository;
import com.anurag.financetracker.repository.UserRepository;


@Service
public class BudgetService {

    private final BudgetRepository budgetRepository;
    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;

    public BudgetService(
        BudgetRepository budgetRepository,
        UserRepository userRepository,
        TransactionRepository transactionRepository) {

                this.budgetRepository = budgetRepository;
                this.userRepository = userRepository;
                this.transactionRepository = transactionRepository;
        }


    public ApiResponse<BudgetResponse> addBudget(AddBudgetRequest request) {
        User user = userRepository.findById(request.getUserId()).orElseThrow(() -> new RuntimeException("User not found"));
        
        
        boolean exists = budgetRepository.existsByUserAndCategoryAndBudgetMonth(
                user,
                request.getCategory(),
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
        budget.setCategory(request.getCategory());
        budget.setBudgetAmount(request.getBudgetAmount());

        Budget savedBudget = budgetRepository.save(budget);

        BudgetResponse response = new BudgetResponse();

        response.setId(savedBudget.getId());
        response.setBudgetMonth(savedBudget.getBudgetMonth());
        response.setCategory(savedBudget.getCategory());
        response.setBudgetAmount(savedBudget.getBudgetAmount());

        return new ApiResponse<>(
                true,
                "Budget added successfully",
                response
        );
    }

    public ApiResponse<String> deleteBudget(Integer id) {

        Budget budget = budgetRepository.findById(id).orElseThrow(() -> new RuntimeException("Budget not found"));

        budgetRepository.delete(budget);

        return new ApiResponse<>(
                true,
                "Budget deleted successfully",
                null
        );
}

    public ApiResponse<BudgetResponse> updateBudget(
        Integer id,
        UpdateBudgetRequest request) {

        Budget budget = budgetRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Budget not found"));

        boolean exists = budgetRepository.existsByUserAndCategoryAndBudgetMonthAndIdNot(
                budget.getUser(),
                request.getCategory(),
                request.getBudgetMonth(),
                id
        );

        if (exists) {
            throw new RuntimeException(
                    "Another budget already exists for this user, category and month"
            );
        }

        budget.setBudgetMonth(request.getBudgetMonth());
        budget.setCategory(request.getCategory());
        budget.setBudgetAmount(request.getBudgetAmount());

        Budget updatedBudget = budgetRepository.save(budget);

        BudgetResponse response = new BudgetResponse();

        response.setId(updatedBudget.getId());
        response.setBudgetMonth(updatedBudget.getBudgetMonth());
        response.setCategory(updatedBudget.getCategory());
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
            response.setCategory(budget.getCategory());
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
        response.setCategory(budget.getCategory());
        response.setBudgetAmount(budget.getBudgetAmount());

        return new ApiResponse<>(
                true,
                "Budget fetched successfully",
                response
        );
    }

    public ApiResponse<BudgetSummaryResponse> getBudgetSummary(Integer id) {

        Budget budget = budgetRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Budget not found"));

        LocalDate startDate = budget.getBudgetMonth();
        LocalDate endDate = startDate.plusMonths(1);

        Double spentAmount = transactionRepository.getTotalExpense(
                budget.getUser(),
                budget.getCategory(),
                startDate,
                endDate,
                TransactionType.EXPENSE
        );

        Double remainingAmount = budget.getBudgetAmount() - spentAmount;

        BudgetSummaryResponse response = new BudgetSummaryResponse();

        response.setBudgetAmount(budget.getBudgetAmount());
        response.setSpentAmount(spentAmount);
        response.setRemainingAmount(remainingAmount);

        return new ApiResponse<>(
                true,
                "Budget summary fetched successfully",
                response
        );
    }
}
