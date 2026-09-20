package com.anurag.financetracker.service;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.anurag.financetracker.dto.CategoryExpenseResponse;
import com.anurag.financetracker.dto.DashboardResponse;
import com.anurag.financetracker.dto.MonthlyReportResponse;
import com.anurag.financetracker.entity.Budget;
import com.anurag.financetracker.entity.Transaction;
import com.anurag.financetracker.entity.User;
import com.anurag.financetracker.enums.TransactionType;
import com.anurag.financetracker.repository.BudgetRepository;
import com.anurag.financetracker.repository.TransactionRepository;
import com.anurag.financetracker.repository.UserRepository;


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

    public List<MonthlyReportResponse> getMonthlyReport() {

        User user = getLoggedInUser();

        LocalDate currentMonth = LocalDate.now().withDayOfMonth(1);
        LocalDate startDate = currentMonth.minusMonths(5);
        LocalDate endDate = currentMonth.plusMonths(1);

        List<Transaction> transactions =
                transactionRepository.findByUserAndTransactionDateBetween(
                        user,
                        startDate,
                        endDate
                );

        Map<String, List<Transaction>> monthlyTransactions =
        transactions.stream()
                .collect(Collectors.groupingBy(
                        transaction -> transaction.getTransactionDate()
                                .format(DateTimeFormatter.ofPattern("yyyy-MM"))
                ));

        List<MonthlyReportResponse> report = new ArrayList<>();
        for (Map.Entry<String, List<Transaction>> entry : monthlyTransactions.entrySet()) {

                String month = entry.getKey();
                List<Transaction> monthTransactions = entry.getValue();

                Double income = monthTransactions.stream()
                        .filter(t -> t.getType() == TransactionType.INCOME)
                        .mapToDouble(Transaction::getAmount)
                        .sum();

                Double expense = monthTransactions.stream()
                        .filter(t -> t.getType() == TransactionType.EXPENSE)
                        .mapToDouble(Transaction::getAmount)
                        .sum();

                report.add(
                        new MonthlyReportResponse(
                                month,
                                income,
                                expense
                        )
                );
                
        }

        return report;

    }

}
