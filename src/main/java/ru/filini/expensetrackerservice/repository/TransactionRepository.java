package ru.filini.expensetrackerservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.filini.expensetrackerservice.model.Transaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByAmountGreaterThanAndDateTimeBetween(BigDecimal amount, LocalDateTime startDate, LocalDateTime endDate);
}
