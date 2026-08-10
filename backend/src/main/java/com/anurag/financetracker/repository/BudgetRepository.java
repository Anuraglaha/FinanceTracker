package com.anurag.financetracker.repository;

import com.anurag.financetracker.entity.Budget;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BudgetRepository extends JpaRepository<Budget, Integer> {

}