package ru.filini.expensetrackerservice.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.filini.expensetrackerservice.model.ExchangeRate;
import ru.filini.expensetrackerservice.repository.ExchangeRateRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

@Service
@Transactional
public class ExchangeRateService {

    @Autowired
    private ExchangeRateRepository exchangeRateRepository;

    //сохраняем обменный курс в СУБД
    public void saveExchangeRate(String sourceCurrency, String targetCurrency, BigDecimal rate, LocalDate date) {
        ExchangeRate exchangeRate = new ExchangeRate();
        exchangeRate.setSourceCurrency(sourceCurrency);
        exchangeRate.setTargetCurrency(targetCurrency);
        exchangeRate.setRate(rate);
        exchangeRate.setDate(date);
        exchangeRateRepository.save(exchangeRate);
    }

    //обновление курса между указанными валютами в указанную дату
    public void updateExchangeRate(String baseCurrency, String targetCurrency, BigDecimal rate, LocalDate date) {
        Optional<ExchangeRate> exchangeRateOptional = exchangeRateRepository.findExchangeRateBySourceCurrencyAndTargetCurrencyAndDate(baseCurrency, targetCurrency, date);
        if (exchangeRateOptional.isPresent()) {
            ExchangeRate exchangeRate = exchangeRateOptional.get();
            exchangeRate.setRate(rate);
            exchangeRateRepository.save(exchangeRate);
        } else {
            throw new RuntimeException("Exchange rate not found");
        }
    }

    //возвращает курс обмена валют в текущую дату
    public BigDecimal getExchangeRate(String baseCurrency, String targetCurrency, LocalDate date) {
        Optional<ExchangeRate> exchangeRateOptional = exchangeRateRepository.findExchangeRateBySourceCurrencyAndTargetCurrencyAndDate(baseCurrency, targetCurrency, date);
        if (exchangeRateOptional.isPresent()) {
            return exchangeRateOptional.get().getRate();
        } else {
            throw new RuntimeException("Exchange rate not found");
        }
    }

    //возвращает текущий курс обмена валют
    public BigDecimal getExchangeRate(String baseCurrency, String targetCurrency) {
        LocalDate currentDate = LocalDate.now();
        return getExchangeRate(baseCurrency, targetCurrency, currentDate);
    }
}