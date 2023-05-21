package com.github.samen66.telegram_bot.command;

import com.github.samen66.telegram_bot.service.SendBotMessageService;
import com.pengrad.telegrambot.model.Update;
import org.springframework.beans.factory.annotation.Autowired;

public class StartCommand implements Command {
    private final SendBotMessageService sendBotMessageService;

    public final static String START_MESSAGE = "Привет. Я Bot.Я помогу вам рассчитать обменный курс.";

    @Autowired
    public StartCommand(SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
    }

    @Override
    public void execute(Update update) {
        sendBotMessageService.sendMessage(update.message().chat().id().toString(), START_MESSAGE);
    }
}
