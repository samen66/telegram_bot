package com.github.samen66.telegram_bot.service;

import com.github.samen66.telegram_bot.MyBot;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SendBotMessageServiceImpl implements SendBotMessageService{

    private final MyBot myBot;

    @Autowired
    public SendBotMessageServiceImpl(MyBot myBot) {
        this.myBot = myBot;
    }

    @Override
    public void sendMessage(String chatId, String message) {
        SendMessage sendMessage = new SendMessage(chatId, message);
        myBot.execute(sendMessage);
    }
}
