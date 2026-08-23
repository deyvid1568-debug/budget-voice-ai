package com.github.dev.deyvid.finance.budget_voice_ai.config;

import com.github.dev.deyvid.finance.budget_voice_ai.dto.CategoryExpenseRequest;
import com.github.dev.deyvid.finance.budget_voice_ai.dto.RegisterTransactionRequest;
import com.github.dev.deyvid.finance.budget_voice_ai.model.Transaction;
import com.github.dev.deyvid.finance.budget_voice_ai.service.TransactionService;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

@Configuration
public class BudgetToolsConfig {

    private final TransactionService transactionService;

    public BudgetToolsConfig(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @Tool(description = "Registra uma nova transação financeira (receita ou despesa) no banco de dados.")
    public String registerTransactionTool(RegisterTransactionRequest request) {
        Transaction saved = transactionService.saveTransaction(
                request.description(),
                request.amount(),
                request.category(),
                request.type()
        );
        return String.format("Transação registrada com sucesso: ID %d, %s de R$ %.2f na categoria %s.",
                saved.getId(), saved.getType(), saved.getAmount(), saved.getCategory());
    }

    @Tool(description = "Consulta o saldo financeiro total atual (receitas acumuladas menos despesas).")
    public String getBalanceTool() {
        BigDecimal balance = transactionService.getBalance();
        return String.format("O saldo financeiro atual é de R$ %.2f.", balance);
    }

    @Tool(description = "Calcula o total de despesas gastas em uma categoria específica.")
    public String getExpensesByCategoryTool(CategoryExpenseRequest request) {
        BigDecimal total = transactionService.getTotalExpensesByCategory(request.category());
        return String.format("O total gasto na categoria '%s' foi de R$ %.2f.", request.category(), total);
    }
}