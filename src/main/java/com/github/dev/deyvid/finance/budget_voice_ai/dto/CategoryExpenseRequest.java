package com.github.dev.deyvid.finance.budget_voice_ai.dto;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;

public record CategoryExpenseRequest(
        @JsonPropertyDescription("Nome da categoria a ser consultada, ex: Alimentação, Transporte")
        String category
) {}