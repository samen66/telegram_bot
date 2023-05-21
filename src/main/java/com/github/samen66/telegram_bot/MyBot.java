package com.github.samen66.telegram_bot;

import com.github.samen66.telegram_bot.command.CommandContainer;
import com.github.samen66.telegram_bot.service.*;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Objects;

import static com.github.samen66.telegram_bot.command.CommandName.EXCHANGE;
import static com.github.samen66.telegram_bot.command.CommandName.NO;

@Component
public class MyBot{

    Logger logger = LoggerFactory.getLogger(MyBot.class);
    private final TelegramBot telegramBot;
    public static String COMMAND_PREFIX = "/";
    private final CommandContainer commandContainer;

    @Autowired
    public MyBot(TelegramBot telegramBot, ExchangeApiService exchangeApiService,
                 CurrencyService currencyService, TelegramUserService telegramUserService,
                 UserMessageService userMessageService) {
        this.telegramBot = telegramBot;
        commandContainer = new CommandContainer(new SendBotMessageServiceImpl(this),
                exchangeApiService, currencyService, telegramUserService, userMessageService);
        listenUp();
    }

    public void listenUp(){
        telegramBot.setUpdatesListener(updates -> {
            // ... process updates
            try {
                updates.forEach(update -> {
                    long chatId = update.message().chat().id();
                    try {
                        onUpdateReceived(update);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
            } catch (NullPointerException e) {
                logger.error(e.getMessage());
            }
            // return id of last processed update or confirm them all
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        // Create Exception Handler
        }, e -> {
            if (e.response() != null) {
                // got bad response from telegram
                logger.error(e.getMessage());
//                e.response().errorCode();
//                e.response().description();
            } else {
                // probably network error
                logger.error(e.getMessage());
//                e.printStackTrace();
            }
        });
    }

    public void onUpdateReceived(Update update) throws IOException {
        String message = update.message().text().trim();
        String chatId = update.message().chat().id().toString();
        if (message.startsWith(COMMAND_PREFIX)) {
            String commandIdentifier = message.split(" ")[0].toLowerCase();

            commandContainer.retrieveCommand(commandIdentifier).execute(update);
        } else {
            commandContainer.retrieveCommand(EXCHANGE.getCommandName()).execute(update);
        }

    }

    public void execute(SendMessage sm) {
        telegramBot.execute(sm);
    }

    private String exchange(String from, String to, Integer amount) throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        String url = String.format("https://api.apilayer.com/currency_data/convert?to=%s&from=%s&amount=%d", to, from, amount);
        System.out.println(url);
        Request request = new Request.Builder()
                .url(url)
                .addHeader("apikey", "iLlT48cMeeP68kvOLMFAk5wGvwPShkYv")
                .build();
        Response response = client.newCall(request).execute();

        System.out.println(Objects.requireNonNull(response.body()).string());
        return Objects.requireNonNull(response.body()).string();
    }
}
