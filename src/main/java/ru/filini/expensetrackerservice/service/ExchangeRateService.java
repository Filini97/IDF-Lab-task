package ru.filini.expensetrackerservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.filini.expensetrackerservice.model.ExchangeRate;
import ru.filini.expensetrackerservice.repository.ExchangeRateRepository;

@Service
public class ExchangeRateService {

    private static final String API_KEY = "72d9bbf926d649b297e3b99e5dedc2c6";
    private static final String API_URL = "https://api.twelvedata.com/time_series";

    @Autowired
    private ExchangeRateRepository exchangeRateRepository;

    @Autowired
    private RestTemplate restTemplate;

    public void fetchAndSaveExchangeRates() {
        //запрос для KZT/USD
        String kztUsdUrl = buildUrl("KZT/USD");
        ExchangeRate kztUsdExchangeRate = fetchExchangeRate(kztUsdUrl);
        exchangeRateRepository.save(kztUsdExchangeRate);

        //запрос для RUB/USD
        String rubUsdUrl = buildUrl("RUB/USD");
        ExchangeRate rubUsdExchangeRate = fetchExchangeRate(rubUsdUrl);
        exchangeRateRepository.save(rubUsdExchangeRate);
    }

    //запрос с указанием валютной пары
    private String buildUrl(String symbol) {
        return String.format("%s?symbol=%s&interval=1day&apikey=%s", API_URL, symbol, API_KEY);
    }

    //отправка запроса к внешнему API
    private ExchangeRate fetchExchangeRate(String url) {
        ExchangeRate exchangeRate = restTemplate.getForObject(url, ExchangeRate.class);
        if (exchangeRate != null) {
            return exchangeRate;
        } else {
            //в случае, если данные не удалось получить, вернуть последний курс из базы данных
            return exchangeRateRepository.findTopBySourceCurrencyAndTargetCurrencyOrderByDateDesc("KZT", "USD")
                    .orElseThrow(() -> new RuntimeException("Unable to fetch exchange rate"));
        }
    }
}