package ru.filini.expensetrackerservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.filini.expensetrackerservice.model.ExpenseLimit;
import ru.filini.expensetrackerservice.repository.ExpenseLimitRepository;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
public class ExpenseLimitService {

    @Autowired
    private ExpenseLimitRepository expenseLimitRepository;

    //устанавливаем лимит
    public void setExpenseLimit(BigDecimal newLimit, String category, boolean isGoods) {
        ExpenseLimit existingLimit = expenseLimitRepository.findByCategory(category);
        if (existingLimit != null) {
            if (isGoods) {
                existingLimit.setGoodsLimit(newLimit);
                existingLimit.setGoodsBalanceOfLimit(newLimit);
            } else {
                existingLimit.setServicesLimit(newLimit);
                existingLimit.setServicesBalanceOfLimit(newLimit);
            }
        } else {
            //создаем новый лимит для указанной категории
            ExpenseLimit newExpenseLimit = new ExpenseLimit();
            newExpenseLimit.setCategory(category);
            if (isGoods) {
                newExpenseLimit.setGoodsLimit(newLimit);
            } else {
                newExpenseLimit.setServicesLimit(newLimit);
            }
            newExpenseLimit.setLimitSetDate(LocalDate.now());
            expenseLimitRepository.save(newExpenseLimit);
        }
    }

    //получение месячного лимита для указанной категории
    public BigDecimal getLimitForCategory(String category) {
        ExpenseLimit expenseLimit = expenseLimitRepository.findByCategory(category);

        return "goods".equals(category) ? expenseLimit.getGoodsLimit() : expenseLimit.getServicesLimit();
    }
}
