package com.github.samen66.telegram_bot.command;

import com.github.samen66.telegram_bot.model.TelegramUser;
import com.github.samen66.telegram_bot.model.UserMessage;
import com.github.samen66.telegram_bot.service.SendBotMessageService;
import com.github.samen66.telegram_bot.service.TelegramUserService;
import com.github.samen66.telegram_bot.service.UserMessageService;
import com.pengrad.telegrambot.model.Update;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Collections;
@Component
@Transactional
public class StartCommand implements Command {
    private final SendBotMessageService sendBotMessageService;
    private final TelegramUserService telegramUserService;
    private final UserMessageService userMessageService;

    public final static String START_MESSAGE = "Привет. Я Bot.Я помогу вам рассчитать обменный курс.\n"+
            "\n" +
            "Этот бот может обменять доллар на тенге или тенге на доллар. Чтобы использовать отправьте {number}$ для обмена доллара на тенге и отправьте {number} тенге для обмена тенге на доллар.";

    @Autowired
    public StartCommand(SendBotMessageService sendBotMessageService, TelegramUserService telegramUserService, UserMessageService userMessageService) {
        this.sendBotMessageService = sendBotMessageService;
        this.telegramUserService = telegramUserService;
        this.userMessageService = userMessageService;
    }

    @Override
    public void execute(Update update) {
        String chatId = update.message().chat().id().toString();

        UserMessage userMessage = new UserMessage();
        userMessage.setRecivedTime(LocalDateTime.now());
        userMessage.setTextMessageContent(update.message().text());

        TelegramUser telegramUser = telegramUserService.getTelegramUserByChatId(chatId);
        if (telegramUser == null){
            telegramUser = new TelegramUser();
            telegramUser.setChatId(chatId);
            telegramUser = telegramUserService.save(telegramUser);
            telegramUser.setUserMessageList(Collections.singletonList(userMessage));
            userMessage.setTelegramUser(telegramUser);
            userMessageService.save(userMessage);
        }else{
            telegramUser.getUserMessageList().add(userMessage);
            userMessage.setTelegramUser(telegramUser);
            userMessageService.save(userMessage);
        }
        sendBotMessageService.sendMessage(update.message().chat().id().toString(), START_MESSAGE);
    }
}
