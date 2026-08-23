# 💰 Budget Voice AI - Assistente Financeiro Inteligente

API REST desenvolvida em Java com Spring Boot e Spring AI para gerenciamento de finanças pessoais por meio de comandos de texto e voz.

---

## 🛠️ Tecnologias Utilizadas

* **Java 21**
* **Spring Boot 4.x** (Web, Data JPA)
* **Spring AI** (Integração Google GenAI / Gemini)
* **H2 Database** (Persistência em arquivo)
* **Lombok**
* **Maven**

---

## 🚀 Melhorias Implementadas

A partir do desafio proposto, foram desenvolvidas as seguintes evoluções na aplicação:

1. **Tool Calling para Consultas Financeiras:**
    * `getBalanceTool`: Permite à IA consultar o saldo líquido consolidado (Receitas - Despesas).
    * `getExpensesByCategoryTool`: Permite à IA calcular os gastos agrupados por uma categoria específica (ex: Alimentação, Transporte, Lazer).
2. **Camada de Serviço com Validação:**
    * Centralização da lógica de negócios em `TransactionService`.
    * Bloqueio e validação contra valores zerados ou negativos no registro de transações.
3. **Mapeamento de DTOs e Tipagem Forte:**
    * Criação de records com descrições detalhadas (`@JsonPropertyDescription`) para orientar o modelo de IA sobre os parâmetros esperados.

---

## ⚙️ Como Executar a Aplicação

### 1. Pré-requisitos
* JDK 21 instalado
* Maven configurado
* Chave de API do Google Gemini

### 2. Variável de Ambiente
Configure sua chave no terminal antes de iniciar a aplicação:

```bash
# Windows PowerShell
$env:GEMINI_API_KEY="sua_chave_aqui"

# Linux/macOS
export GEMINI_API_KEY="sua_chave_aqui"