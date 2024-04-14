package ru.filini.expensetrackerservice.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import ru.filini.expensetrackerservice.model.Transaction;
import ru.filini.expensetrackerservice.repository.TransactionRepository;

import java.math.BigDecimal;
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

    //помечает транзакции, превысившие месячный лимит
    public void markTransactionsExceedingLimit(List<Transaction> transactions, BigDecimal goodsMonthlyLimit, BigDecimal servicesMonthlyLimit) {
        for (Transaction transaction : transactions) {
            BigDecimal monthlyLimit;
            //определяем месячный лимит в зависимости от категории
            if ("goods".equals(transaction.getType())) {
                monthlyLimit = goodsMonthlyLimit;
            } else if ("services".equals(transaction.getType())) {
                monthlyLimit = servicesMonthlyLimit;
            } else {
                //если категория не соответствует ни "goods", ни "services", то пропускаем транзакцию
                //log.warn("Unknown transaction type: {}", transaction.getType());
                continue;
            }
            //помечаем транзакцию, если ее сумма превышает месячный лимит
            if (transaction.getAmount().compareTo(monthlyLimit) > 0) {
                transaction.setLimitExceeded(true);
                transactionRepository.save(transaction);
            }
        }
    }

    public List<Transaction> findTransactionsExceedingLimit() {
        return transactionRepository.findTransactionsExceedingLimit();
    }
}
