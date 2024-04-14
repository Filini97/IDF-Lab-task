package ru.filini.expensetrackerservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.filini.expensetrackerservice.model.Transaction;
import ru.filini.expensetrackerservice.service.TransactionService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping
    //получаем списко всех транзакций
    public ResponseEntity<List<Transaction>> getAllTransactions() {
        List<Transaction> transactions = transactionService.getAllTransactions();
        return ResponseEntity.ok(transactions);
    }

    @PostMapping
    //новая транзакция
    public ResponseEntity<Transaction> createTransaction(@RequestBody Transaction transaction) {
        Transaction createdTransaction = transactionService.saveTransaction(transaction);
        return ResponseEntity.created(URI.create("/transactions/" + createdTransaction.getId())).body(createdTransaction);
    }

    @GetMapping("/exceeding-limit")
    public ResponseEntity<List<Transaction>> getTransactionsExceedingLimit() {
        List<Transaction> transactions = transactionService.findTransactionsExceedingLimit();
        return ResponseEntity.ok(transactions);
    }
}
