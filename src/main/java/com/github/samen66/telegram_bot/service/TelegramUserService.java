package com.github.samen66.telegram_bot.service;

import com.github.samen66.telegram_bot.model.TelegramUser;

import java.util.List;

public interface TelegramUserService {
    List<TelegramUser> getAllTelegramUser();
    TelegramUser getTelegramUserById(Integer userId);
    TelegramUser getTelegramUserByChatId(String chatId);
    TelegramUser save(TelegramUser telegramUser);

}
