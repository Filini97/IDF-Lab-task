package ru.filini.expensetrackerservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.filini.expensetrackerservice.service.ExpenseLimitService;

import java.math.BigDecimal;

@RestController
@RequestMapping("/expense-limits")
public class ExpenseLimitController {
    @Autowired
    private ExpenseLimitService expenseLimitService;

    //установка нового лимита на расходы
    @PostMapping("/set-limit")
    public ResponseEntity<Void> setExpenseLimit(
            @RequestParam BigDecimal newLimit,
            @RequestParam String category,
            @RequestParam boolean isGoods) {
        expenseLimitService.setExpenseLimit(newLimit, category, isGoods);
        return ResponseEntity.ok().build();
    }
}
