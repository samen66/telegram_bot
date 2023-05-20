package com.github.samen66.telegram_bot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import org.springframework.beans.factory.annotation.Autowired;

public class MyBot{
    private final TelegramBot telegramBot;

    @Autowired
    public MyBot(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
        listenUp();
    }

    public void listenUp(){
        telegramBot.setUpdatesListener(updates -> {
            // ... process updates
            updates.forEach(update1 -> {
                long chatId = update1.message().chat().id();
                SendResponse response = telegramBot.execute(new SendMessage(chatId, "Hello!"));
            });
            // return id of last processed update or confirm them all
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        // Create Exception Handler
        }, e -> {
            if (e.response() != null) {
                // got bad response from telegram
                e.response().errorCode();
                e.response().description();
            } else {
                // probably network error
                e.printStackTrace();
            }
        });
    }
}
