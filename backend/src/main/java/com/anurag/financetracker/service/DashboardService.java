package com.anurag.financetracker.service;
import java.util.List;

import com.anurag.financetracker.dto.CategoryExpenseResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import com.anurag.financetracker.dto.DashboardResponse;
import com.anurag.financetracker.entity.User;
import com.anurag.financetracker.repository.BudgetRepository;
import java.util.List;
import com.anurag.financetracker.entity.Budget;
import com.anurag.financetracker.repository.TransactionRepository;
import com.anurag.financetracker.repository.UserRepository;
import com.anurag.financetracker.enums.TransactionType;


@Service
public class DashboardService {

    private final TransactionRepository transactionRepository;
    private final BudgetRepository budgetRepository;
    private final UserRepository userRepository;

    public DashboardService(
            TransactionRepository transactionRepository,
            BudgetRepository budgetRepository,
            UserRepository userRepository) {

        this.transactionRepository = transactionRepository;
        this.budgetRepository = budgetRepository;
        this.userRepository = userRepository;
    }
    private User getLoggedInUser() {

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public DashboardResponse getDashboard() {

        User user = getLoggedInUser();

        LocalDate startDate = LocalDate.now().withDayOfMonth(1);
        LocalDate endDate = startDate.plusMonths(1);

        Double totalIncome = transactionRepository.getTotalAmount(
                user,
                startDate,
                endDate,
                TransactionType.INCOME
        );

        Double totalExpense = transactionRepository.getTotalAmount(
            user,
            startDate,
            endDate,
            TransactionType.EXPENSE
        );

        Double balance = totalIncome - totalExpense;

        List<Budget> budgets = budgetRepository.findByUserAndBudgetMonth(
                user,
                startDate
        );

        Double totalBudget = budgets.stream().mapToDouble(Budget::getBudgetAmount).sum();

        Integer budgetCount = budgets.size();

        DashboardResponse response = new DashboardResponse();

        response.setTotalIncome(totalIncome);
        response.setTotalExpense(totalExpense);
        response.setBalance(balance);
        response.setTotalBudget(totalBudget);
        response.setBudgetCount(budgetCount);

        return response;
    }

    public List<CategoryExpenseResponse> getCategoryWiseExpenses() {

        User user = getLoggedInUser();

        LocalDate startDate = LocalDate.now().withDayOfMonth(1);
        LocalDate endDate = startDate.plusMonths(1);

        List<Object[]> results = transactionRepository.getCategoryWiseAmount(
                user,
                startDate,
                endDate,
                TransactionType.EXPENSE
        );

        return results.stream()
                .map(row -> new CategoryExpenseResponse(
                        row[0].toString(),
                        ((Number) row[1]).doubleValue()
                ))
                .toList();
    }

}