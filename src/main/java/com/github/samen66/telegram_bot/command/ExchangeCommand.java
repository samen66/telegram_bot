package com.github.samen66.telegram_bot.command;

import com.github.samen66.telegram_bot.service.ExchangeApiService;
import com.github.samen66.telegram_bot.service.SendBotMessageService;
import com.pengrad.telegrambot.model.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class ExchangeCommand implements Command {
    private final SendBotMessageService sendBotMessageService;
    private final ExchangeApiService exchangeApiService;

    public final static StringBuilder START_MESSAGE = new StringBuilder("");

    @Autowired
    public ExchangeCommand(SendBotMessageService sendBotMessageService, ExchangeApiService exchangeApiService) {
        this.sendBotMessageService = sendBotMessageService;
        this.exchangeApiService = exchangeApiService;
    }

    @Override
    public void execute(Update update) {
        String dollar = "USD";
        String kz = "KZT";
        StringBuilder result = new StringBuilder("");
        String message = update.message().text();
        if (message.contains("$")) {
            String amount = message.split("\\$")[0];
            try {
                List<String> stringList = new ArrayList<>();
                stringList.add(kz);
                exchangeApiService.exchange(stringList).forEach(System.out::println);
            } catch (NumberFormatException e) {
                result.append("Error");
            }
            System.out.println(amount);
        }else{
            String amount = message.split(" ")[0];
            try {
                result.append(exchangeApiService.exchange(dollar, kz, Integer.valueOf(amount)));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            System.out.println(result.toString());
        }
        sendBotMessageService.sendMessage(update.message().chat().id().toString(), result.toString());
    }
}
