package com.github.samen66.telegram_bot.repository;

import com.github.samen66.telegram_bot.model.TelegramUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TelegramUserRepository extends JpaRepository<TelegramUser, Integer> {
    TelegramUser findFirstByChatId(String chatId);
}
