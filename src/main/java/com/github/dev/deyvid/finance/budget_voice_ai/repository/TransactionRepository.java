package com.github.dev.deyvid.finance.budget_voice_ai.repository;

import com.github.dev.deyvid.finance.budget_voice_ai.model.Transaction;
import com.github.dev.deyvid.finance.budget_voice_ai.model.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query("SELECT SUM(t.amount) FROM Transaction t WHERE t.type = :type")
    BigDecimal sumAmountByType(@Param("type") TransactionType type);

    @Query("SELECT SUM(t.amount) FROM Transaction t WHERE t.category = :category AND t.type = :type")
    BigDecimal sumAmountByCategoryAndType(@Param("category") String category, @Param("type") TransactionType type);

}