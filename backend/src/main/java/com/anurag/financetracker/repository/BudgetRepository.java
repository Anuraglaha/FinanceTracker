package com.anurag.financetracker.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.anurag.financetracker.entity.Budget;
import com.anurag.financetracker.entity.User;
import com.anurag.financetracker.enums.Category;

public interface BudgetRepository extends JpaRepository<Budget, Integer> {
    List<Budget> findByUser(User user);

    List<Budget> findByUserAndBudgetMonth(
        User user,
        LocalDate budgetMonth
    );
    
    boolean existsByUserAndCategoryAndBudgetMonth(
        User user,
        Category category,
        LocalDate budgetMonth
    );

    boolean existsByUserAndCategoryAndBudgetMonthAndIdNot(
        User user,
        Category category,
        LocalDate budgetMonth,
        Integer id
    );

}