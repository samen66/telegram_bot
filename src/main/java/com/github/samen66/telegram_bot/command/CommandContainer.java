package com.github.samen66.telegram_bot.command;

import com.github.samen66.telegram_bot.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;

import static com.github.samen66.telegram_bot.command.CommandName.*;
@Component
public class CommandContainer {
    private final HashMap<String, Command> commandMap;
    private final Command unknownCommand;

    @Autowired
    public CommandContainer(SendBotMessageService sendBotMessageService, ExchangeApiService exchangeApiService, CurrencyService currencyService,
                            TelegramUserService telegramUserService, UserMessageService userMessageService) {

        commandMap = new HashMap<>();
        commandMap.put(START.getCommandName(), new StartCommand(sendBotMessageService, telegramUserService, userMessageService));
        commandMap.put(STOP.getCommandName(), new StopCommand(sendBotMessageService));
        commandMap.put(HELP.getCommandName(), new HelpCommand(sendBotMessageService, telegramUserService, userMessageService));
        commandMap.put(NO.getCommandName(), new NoCommand(sendBotMessageService));
        commandMap.put(EXCHANGE.getCommandName(), new ExchangeCommand(sendBotMessageService, exchangeApiService, currencyService, telegramUserService, userMessageService));

        unknownCommand = new UnknownCommand(sendBotMessageService);
    }

    public Command retrieveCommand(String commandIdentifier) {
        return commandMap.getOrDefault(commandIdentifier, unknownCommand);
    }
}
