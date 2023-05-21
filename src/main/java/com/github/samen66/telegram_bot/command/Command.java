package com.github.samen66.telegram_bot.command;

import com.pengrad.telegrambot.model.Update;

public interface Command {
    void execute(Update update);
}
