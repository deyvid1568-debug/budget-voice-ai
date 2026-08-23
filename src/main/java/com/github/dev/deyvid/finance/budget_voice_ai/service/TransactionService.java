package com.github.dev.deyvid.finance.budget_voice_ai.service;

import com.github.dev.deyvid.finance.budget_voice_ai.model.Transaction;
import com.github.dev.deyvid.finance.budget_voice_ai.model.TransactionType;
import com.github.dev.deyvid.finance.budget_voice_ai.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository repository;

    @Transactional
    public Transaction saveTransaction(String description, BigDecimal amount, String category, TransactionType type) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("O valor da transação deve ser positivo.");
        }

        Transaction transaction = Transaction.builder()
                .description(description)
                .amount(amount)
                .category(category != null ? category.trim() : "Outros")
                .type(type != null ? type : TransactionType.DESPESA)
                .date(LocalDate.now())
                .build();

        return repository.save(transaction);
    }

    @Transactional(readOnly = true)
    public BigDecimal getBalance() {
        BigDecimal receitas = repository.sumAmountByType(TransactionType.RECEITA);
        BigDecimal despesas = repository.sumAmountByType(TransactionType.DESPESA);
        return receitas.subtract(despesas);
    }

    @Transactional(readOnly = true)
    public BigDecimal getTotalExpensesByCategory(String category) {
        return repository.sumAmountByCategoryAndType(category, TransactionType.DESPESA);
    }

    @Transactional(readOnly = true)
    public List<Transaction> listAll() {
        return repository.findAll();
    }
}