package com.github.dev.deyvid.finance.budget_voice_ai.dto;

import com.github.dev.deyvid.finance.budget_voice_ai.model.TransactionType;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import java.math.BigDecimal;

public record RegisterTransactionRequest(
        @JsonPropertyDescription("Descrição detalhada do gasto ou ganho, ex: Almoço no restaurante, Salário")
        String description,

        @JsonPropertyDescription("Valor monetário numérico positivo da transação, ex: 45.50")
        BigDecimal amount,

        @JsonPropertyDescription("Categoria da transação, ex: Alimentação, Transporte, Moradia, Salário, Lazer")
        String category,

        @JsonPropertyDescription("Tipo da movimentação: RECEITA ou DESPESA")
        TransactionType type
) {}