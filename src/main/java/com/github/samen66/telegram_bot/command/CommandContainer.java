package com.github.samen66.telegram_bot.command;

import com.github.samen66.telegram_bot.service.ExchangeApiService;
import com.github.samen66.telegram_bot.service.SendBotMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.LinkedHashMap;

import static com.github.samen66.telegram_bot.command.CommandName.*;
@Component
public class CommandContainer {
    private final HashMap<String, Command> commandMap;
    private final Command unknownCommand;

    @Autowired
    public CommandContainer(SendBotMessageService sendBotMessageService, ExchangeApiService exchangeApiService) {

        commandMap = new HashMap<>();
        commandMap.put(START.getCommandName(), new StartCommand(sendBotMessageService));
        commandMap.put(STOP.getCommandName(), new StopCommand(sendBotMessageService));
        commandMap.put(HELP.getCommandName(), new HelpCommand(sendBotMessageService));
        commandMap.put(NO.getCommandName(), new NoCommand(sendBotMessageService));
        commandMap.put(EXCHANGE.getCommandName(), new ExchangeCommand(sendBotMessageService, exchangeApiService));

        unknownCommand = new UnknownCommand(sendBotMessageService);
    }

    public Command retrieveCommand(String commandIdentifier) {
        return commandMap.getOrDefault(commandIdentifier, unknownCommand);
    }
}
