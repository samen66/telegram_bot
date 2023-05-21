package com.github.samen66.telegram_bot.service;

import com.github.samen66.telegram_bot.model.TelegramUser;
import com.github.samen66.telegram_bot.model.UserMessage;

import java.util.List;

public interface UserMessageService {
    List<UserMessage> getUserMessage(Integer userId);
    UserMessage getUserMessageById(Integer userId, Integer messageId);

    void save(UserMessage userMessage);

    void createNewMessage(String messageContent, TelegramUser user);


}
