package com.github.dev.deyvid.finance.budget_voice_ai.controller;

import com.github.dev.deyvid.finance.budget_voice_ai.model.Transaction;
import com.github.dev.deyvid.finance.budget_voice_ai.service.ChatClientService;
import com.github.dev.deyvid.finance.budget_voice_ai.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/budget")
@RequiredArgsConstructor
public class BudgetVoiceController {

    private final ChatClientService chatClientService;
    private final TransactionService transactionService;

    @PostMapping("/chat")
    public ResponseEntity<Map<String, String>> processTextCommand(@RequestBody Map<String, String> payload) {
        String message = payload.get("message");
        if (message == null || message.isBlank()) {
            return ResponseEntity.badRequest().body(Map.of("error", "O campo 'message' é obrigatório."));
        }

        String aiResponse = chatClientService.processUserInput(message);
        return ResponseEntity.ok(Map.of("response", aiResponse));
    }

    @PostMapping(value = "/voice", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_OCTET_STREAM_VALUE})
    public ResponseEntity<Map<String, String>> processVoiceCommand(@RequestParam("file") MultipartFile file) {
        try {
            if (file.isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of("error", "Envie um arquivo de áudio válido."));
            }

            String contentType = file.getContentType();
            if (contentType == null || contentType.equals("application/octet-stream")) {
                contentType = "audio/mp3";
            }

            String aiResponse = chatClientService.processAudioInput(file.getResource(), contentType);

            return ResponseEntity.ok(Map.of("response", aiResponse));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of("error", e.getMessage() != null ? e.getMessage() : "Erro desconhecido"));
        }
    }

    @GetMapping("/transactions")
    public ResponseEntity<List<Transaction>> getAllTransactions() {
        return ResponseEntity.ok(transactionService.listAll());
    }
}