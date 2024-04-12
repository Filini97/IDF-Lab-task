package ru.filini.expensetrackerservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.filini.expensetrackerservice.model.ExpenseLimit;
import ru.filini.expensetrackerservice.model.Transaction;
import ru.filini.expensetrackerservice.repository.ExpenseLimitRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ExpenseLimitService {

    @Autowired
    private ExpenseLimitRepository expenseLimitRepository;

    @Autowired
    private TransactionService transactionService;

    public List<ExpenseLimit> getAllExpenseLimits() {
        return expenseLimitRepository.findAll();
    }

    public void setExpenseLimit(BigDecimal newLimit, String category) {
        ExpenseLimit existingLimit = expenseLimitRepository.findByCategory(category);
        if (existingLimit != null) {
            //обновление лимита
            existingLimit.setMonthlyLimit(newLimit);
            expenseLimitRepository.save(existingLimit);
        } else {
            //создание лимита
            ExpenseLimit newExpenseLimit = new ExpenseLimit();
            newExpenseLimit.setCategory(category);
            newExpenseLimit.setMonthlyLimit(newLimit);
            expenseLimitRepository.save(newExpenseLimit);
        }
    }

    public List<Transaction> getTransactionsExceedingLimit(String category, LocalDateTime startDate, LocalDateTime endDate) {
        ExpenseLimit expenseLimit = expenseLimitRepository.findByCategory(category);
        if (expenseLimit == null) {
            //если лимит отсутсвтует
            return List.of();
        }
        BigDecimal monthlyLimit = expenseLimit.getMonthlyLimit();
        return transactionService.getTransactionsExceedingLimit(monthlyLimit, startDate, endDate);
    }
}
