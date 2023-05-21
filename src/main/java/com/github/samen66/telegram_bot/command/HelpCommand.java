package com.github.samen66.telegram_bot.command;

import com.github.samen66.telegram_bot.model.TelegramUser;
import com.github.samen66.telegram_bot.service.SendBotMessageService;
import com.github.samen66.telegram_bot.service.TelegramUserService;
import com.github.samen66.telegram_bot.service.UserMessageService;
import com.pengrad.telegrambot.model.Update;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.github.samen66.telegram_bot.command.CommandName.*;
@Component
@Transactional
public class HelpCommand implements Command {

    private final SendBotMessageService sendBotMessageService;
    private final TelegramUserService telegramUserService;
    private final UserMessageService userMessageService;

    public static final String HELP_MESSAGE = String.format("✨<b>Дотупные команды</b>✨\n\n"

                    + "<b>Начать\\закончить работу с ботом</b>\n"
                    + "%s - начать работу со мной\n"
                    + "%s - приостановить работу со мной\n\n"
                    + "%s - получить помощь в работе со мной\n",
            START.getCommandName(), STOP.getCommandName(), HELP.getCommandName());
    @Autowired
    public HelpCommand(SendBotMessageService sendBotMessageService, TelegramUserService telegramUserService, UserMessageService userMessageService) {
        this.sendBotMessageService = sendBotMessageService;
        this.telegramUserService = telegramUserService;
        this.userMessageService = userMessageService;
    }

    @Override
    public void execute(Update update) {
        String chatId = update.message().chat().id().toString();
        TelegramUser telegramUser = telegramUserService.getTelegramUserByChatId(chatId);

        userMessageService.createNewMessage(update.message().text(), telegramUser);

        sendBotMessageService.sendMessage(chatId, HELP_MESSAGE);
    }
}
