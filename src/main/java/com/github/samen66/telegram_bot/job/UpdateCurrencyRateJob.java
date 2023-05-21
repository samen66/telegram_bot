package com.github.samen66.telegram_bot.job;

import com.github.samen66.telegram_bot.service.CurrencyService;
import com.github.samen66.telegram_bot.service.ExchangeApiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class UpdateCurrencyRateJob {
    private final ExchangeApiService exchangeApiService;
    private final CurrencyService currencyService;
    @Autowired
    public UpdateCurrencyRateJob(ExchangeApiService exchangeApiService, CurrencyService currencyService) {
        this.exchangeApiService = exchangeApiService;
        this.currencyService = currencyService;
    }

    @Scheduled(cron = "0 0 5 * * *")
    public void updateExchangeRate(){
        LocalDateTime start = LocalDateTime.now();

        log.info("UpdateCurrencyRateJob job started.");

        List<String> allCurencyCodeList = new ArrayList<>();
        currencyService.getAllCurrency().forEach(currency -> {
            allCurencyCodeList.add(currency.getCode());
        });

        exchangeApiService.exchange(allCurencyCodeList).forEach(currencyService::save);

        LocalDateTime end = LocalDateTime.now();

        log.info("UpdateCurrencyRateJob job finished. Took seconds: {}",
                end.toEpochSecond(ZoneOffset.UTC) - start.toEpochSecond(ZoneOffset.UTC));

    }
}
