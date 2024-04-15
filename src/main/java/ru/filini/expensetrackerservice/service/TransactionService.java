package ru.filini.expensetrackerservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.filini.expensetrackerservice.model.ExpenseLimit;
import ru.filini.expensetrackerservice.model.Transaction;
import ru.filini.expensetrackerservice.repository.TransactionRepository;

import java.math.BigDecimal;
import java.util.List;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private ExpenseLimitService expenseLimitService;

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    //сохраняем новую транзакцию и проверяем превышение лимита
    public Transaction saveTransaction(Transaction transaction) {
        BigDecimal limitBalance = expenseLimitService.getLimitBalanceForCategory(transaction.getType());
        BigDecimal limit = expenseLimitService.getLimitBalanceForCategory(transaction.getType());
        BigDecimal transactionAmount = transaction.getAmount();

        transaction.setLimitExceeded(transactionAmount.compareTo(limit) > 0);

        BigDecimal updatedBalance = limitBalance.subtract(transactionAmount);
        expenseLimitService.updateLimitBalanceForCategory(transaction.getType(), updatedBalance);

        return transactionRepository.save(transaction);
    }

    public List<Transaction> findTransactionsExceedingLimit() {
        return transactionRepository.findTransactionsExceedingLimit();
    }
}

