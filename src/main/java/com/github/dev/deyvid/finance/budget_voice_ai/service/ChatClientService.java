package com.github.dev.deyvid.finance.budget_voice_ai.service;

import com.github.dev.deyvid.finance.budget_voice_ai.config.BudgetToolsConfig;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;

@Service
public class ChatClientService {

    private final ChatClient chatClient;
    private final BudgetToolsConfig budgetTools;

    public ChatClientService(ChatClient.Builder chatClientBuilder, BudgetToolsConfig budgetTools) {
        this.budgetTools = budgetTools;
        this.chatClient = chatClientBuilder
                .defaultSystem("""
                    Você é um assistente financeiro pessoal inteligente e amigável.
                    Identifique comandos de voz ou texto sobre receitas, despesas e consultas de saldo/gastos por categoria e execute a ferramenta apropriada.
                    Sempre responda de forma clara, educada e em português do Brasil.
                """)
                .build();
    }

    public String processUserInput(String userMessage) {
        return this.chatClient.prompt()
                .user(userMessage)
                .tools(budgetTools)
                .call()
                .content();
    }

    public String processAudioInput(Resource audioResource, String mimeType) {
        return this.chatClient.prompt()
                .user(u -> u.text("Processe o comando deste áudio financeiro.")
                        .media(MimeTypeUtils.parseMimeType(mimeType), audioResource))
                .tools(budgetTools)
                .call()
                .content();
    }
}