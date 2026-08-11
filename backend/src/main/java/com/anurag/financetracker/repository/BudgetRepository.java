package com.anurag.financetracker.repository;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;

import com.anurag.financetracker.entity.Budget;
import com.anurag.financetracker.entity.User;
import com.anurag.financetracker.enums.BudgetType;

public interface BudgetRepository extends JpaRepository<Budget, Integer> {
    
    boolean existsByUserAndBudgetTypeAndBudgetMonth(
        User user,
        BudgetType budgetType,
        LocalDate budgetMonth
    );

    boolean existsByUserAndBudgetTypeAndBudgetMonthAndIdNot(
        User user,
        BudgetType budgetType,
        LocalDate budgetMonth,
        Integer id
    );

}