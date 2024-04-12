package ru.filini.expensetrackerservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.filini.expensetrackerservice.model.ExpenseLimit;

@Repository
public interface ExpenseLimitRepository extends JpaRepository<ExpenseLimit, Long> {

}
