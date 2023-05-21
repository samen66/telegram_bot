package com.github.samen66.telegram_bot.command;

import com.github.samen66.telegram_bot.model.Currency;
import com.github.samen66.telegram_bot.service.CurrencyService;
import com.github.samen66.telegram_bot.service.ExchangeApiService;
import com.github.samen66.telegram_bot.service.SendBotMessageService;
import com.pengrad.telegrambot.model.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class ExchangeCommand implements Command {
    private final SendBotMessageService sendBotMessageService;
    private final ExchangeApiService exchangeApiService;
    private final CurrencyService currencyService;

    public final static StringBuilder START_MESSAGE = new StringBuilder("");

    @Autowired
    public ExchangeCommand(SendBotMessageService sendBotMessageService, ExchangeApiService exchangeApiService, CurrencyService currencyService) {
        this.sendBotMessageService = sendBotMessageService;
        this.exchangeApiService = exchangeApiService;
        this.currencyService = currencyService;
    }

    @Override
    public void execute(Update update) {
        String dollar = "USD";
        String kz = "KZT";
        StringBuilder result = new StringBuilder("");
        String message = update.message().text();
        Currency currency = currencyService.findByCode(kz);
        if (currency == null){
            exchangeApiService.exchange(new ArrayList<>(Collections.singleton(kz))).
                    forEach(currencyService::save);
            currency = currencyService.findByCode(kz);
        }
        if (message.contains("$")) {
            try {
                Double amount = Double.valueOf(message.split("\\$")[0]);
                result.append(currency.getValue() * amount).append(" tenge");
            } catch (NumberFormatException e) {
                result.append("Error number contains other symbols");
            }
        }else{

            Double amount = null;
            try {
                amount = Double.valueOf(message.split(" ")[0]);
                result.append(amount / currency.getValue()).append(" $");
                System.out.println(result.toString());
            } catch (NumberFormatException e) {
                result.append("number contains other symbols");
            }
        }
        sendBotMessageService.sendMessage(update.message().chat().id().toString(), result.toString());
    }
}
