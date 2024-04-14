package ru.filini.expensetrackerservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
        BigDecimal limit = expenseLimitService.getLimitForCategory(transaction.getType());
        BigDecimal transactionAmount = transaction.getAmount();

        if (transactionAmount.compareTo(limit) > 0) {
            // Если транзакция превышает месячный лимит, помечаем ее
            transaction.setLimitExceeded(true);
        }

        // Сохраняем транзакцию в базе данных
        return transactionRepository.save(transaction);
    }

    public List<Transaction> findTransactionsExceedingLimit() {
        return transactionRepository.findTransactionsExceedingLimit();
    }
}

