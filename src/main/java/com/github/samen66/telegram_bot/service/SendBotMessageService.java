package com.github.samen66.telegram_bot.service;

public interface SendBotMessageService {
    void sendMessage(String chatId, String message);
}
