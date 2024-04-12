package ru.filini.expensetrackerservice.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import ru.filini.expensetrackerservice.model.Transaction;
import ru.filini.expensetrackerservice.repository.TransactionRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    //возвращается список всех транзакций
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    //сохраняет новую транзакцию в базе данных
    public Transaction saveTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    //возвращает список транзакций, превысивших месячный лимит, в указанный временный промежуток
    public List<Transaction> getTransactionsExceedingLimit(BigDecimal monthlyLimit, LocalDateTime startDate, LocalDateTime endDate) {
        return transactionRepository.findByAmountGreaterThanAndDateTimeBetween(monthlyLimit, startDate, endDate);
    }

    //помечает транзакции, превысившие месячный лимит
    public void markTransactionsExceedingLimit(List<Transaction> transactions, BigDecimal monthlyLimit) {
        for (Transaction transaction : transactions) {
            if (transaction.getAmount().compareTo(monthlyLimit) > 0) {
                transaction.setLimitExceeded(true);
                transactionRepository.save(transaction);
            }
        }
    }
}
