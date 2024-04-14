package ru.filini.expensetrackerservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.filini.expensetrackerservice.model.Transaction;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query("SELECT t FROM Transaction t JOIN t.expenseLimit e WHERE t.limitExceeded = true")
    List<Transaction> findTransactionsExceedingLimit();
}
