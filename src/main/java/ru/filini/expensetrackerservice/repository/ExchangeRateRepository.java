package ru.filini.expensetrackerservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.filini.expensetrackerservice.model.ExchangeRate;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface ExchangeRateRepository extends JpaRepository<ExchangeRate, Long> {
    Optional<ExchangeRate> findExchangeRateBySourceCurrencyAndTargetCurrencyAndDate(String sourceCurrency, String targetCurrency, LocalDate date);
}
