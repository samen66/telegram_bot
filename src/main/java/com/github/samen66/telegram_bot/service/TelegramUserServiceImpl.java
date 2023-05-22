package com.github.samen66.telegram_bot.service;

import com.github.samen66.telegram_bot.model.TelegramUser;
import com.github.samen66.telegram_bot.repository.TelegramUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class TelegramUserServiceImpl implements TelegramUserService{
    private final TelegramUserRepository telegramUserRepository;
    @Autowired
    public TelegramUserServiceImpl(TelegramUserRepository telegramUserRepository) {
        this.telegramUserRepository = telegramUserRepository;
    }

    @Override
    public List<TelegramUser> getAllTelegramUser() {
        return telegramUserRepository.findAll();
    }

    @Override
    public TelegramUser getTelegramUserById(Integer userId) {
        return telegramUserRepository.findById(userId).orElse(null);
    }

    @Override
    public TelegramUser getTelegramUserByChatId(String chatId) {
        return telegramUserRepository.findFirstByChatId(chatId);
    }

    @Override
    public TelegramUser save(TelegramUser telegramUser) {
       return telegramUserRepository.save(telegramUser);
    }
}
