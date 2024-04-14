package ru.filini.expensetrackerservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.filini.expensetrackerservice.model.ExpenseLimit;
import ru.filini.expensetrackerservice.repository.ExpenseLimitRepository;

import java.math.BigDecimal;
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

    //устанавливаем лимит
    public void setExpenseLimit(BigDecimal newLimit, String category, boolean isGoods) {
        ExpenseLimit existingLimit = expenseLimitRepository.findByCategory(category);
        if (existingLimit != null) {
            //обновляем лимит для указанной категории
            if (isGoods) {
                existingLimit.setGoodsMonthlyLimit(newLimit);
            } else {
                existingLimit.setServicesMonthlyLimit(newLimit);
            }
            expenseLimitRepository.save(existingLimit);
        } else {
            //создаем новый лимит для указанной категории
            ExpenseLimit newExpenseLimit = new ExpenseLimit();
            newExpenseLimit.setCategory(category);
            if (isGoods) {
                newExpenseLimit.setGoodsMonthlyLimit(newLimit);
            } else {
                newExpenseLimit.setServicesMonthlyLimit(newLimit);
            }
            expenseLimitRepository.save(newExpenseLimit);
        }
    }
}
