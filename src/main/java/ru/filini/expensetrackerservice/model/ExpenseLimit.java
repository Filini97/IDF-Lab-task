package ru.filini.expensetrackerservice.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@Entity
@Table(name = "expense_limits")
public class ExpenseLimit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "goods_monthly_limit", nullable = false)
    private BigDecimal goodsMonthlyLimit;

    @Column(name = "services_monthly_limit", nullable = false)
    private BigDecimal servicesMonthlyLimit;

    @Column(name = "limit_set_date", nullable = false)
    private LocalDate limitSetDate;

    private String category;

    public ExpenseLimit() {
        this.goodsMonthlyLimit = BigDecimal.valueOf(1000);
        this.servicesMonthlyLimit = BigDecimal.valueOf(1000);
        this.limitSetDate = LocalDate.now();
    }
}
