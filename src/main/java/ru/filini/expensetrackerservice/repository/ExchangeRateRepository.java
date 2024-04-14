package ru.filini.expensetrackerservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.filini.expensetrackerservice.model.ExchangeRate;

import java.util.Optional;

@Repository
public interface ExchangeRateRepository extends JpaRepository<ExchangeRate, Long> {
    Optional<ExchangeRate> findTopBySourceCurrencyAndTargetCurrencyOrderByDateDesc(String sourceCurrency, String targetCurrency);
}
