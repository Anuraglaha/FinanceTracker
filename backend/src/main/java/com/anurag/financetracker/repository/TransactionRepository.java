package com.anurag.financetracker.repository;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;


import com.anurag.financetracker.entity.Transaction;
import com.anurag.financetracker.entity.User;
import com.anurag.financetracker.enums.Category;
import com.anurag.financetracker.enums.TransactionType;


public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

    List<Transaction> findByUser(User user);

    List<Transaction> findByUserAndTransactionDateBetween(
        User user,
        LocalDate from,
        LocalDate to
    );

    @Query("""
    SELECT COALESCE(SUM(t.amount), 0)
    FROM Transaction t
    WHERE t.user = :user
      AND t.category = :category
      AND t.transactionDate >= :startDate
      AND t.transactionDate < :endDate
      AND t.type = :type
    """)
    Double getTotalExpense(
            @Param("user") User user,
            @Param("category") Category category,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("type") TransactionType type
    );

    @Query("""
    SELECT COALESCE(SUM(t.amount), 0)
    FROM Transaction t
    WHERE t.user = :user
      AND t.transactionDate >= :startDate
      AND t.transactionDate < :endDate
      AND t.type = :type
    """)
    Double getTotalAmount(
            @Param("user") User user,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("type") TransactionType type
    );

    @Query("""
    SELECT t.category, COALESCE(SUM(t.amount), 0)
    FROM Transaction t
    WHERE t.user = :user
      AND t.transactionDate >= :startDate
      AND t.transactionDate < :endDate
      AND t.type = :type
    GROUP BY t.category
    """)
    List<Object[]> getCategoryWiseAmount(
            @Param("user") User user,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("type") TransactionType type
    );

}
