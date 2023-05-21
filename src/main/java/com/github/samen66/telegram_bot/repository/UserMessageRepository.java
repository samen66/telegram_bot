package com.github.samen66.telegram_bot.repository;

import com.github.samen66.telegram_bot.model.UserMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMessageRepository extends JpaRepository<UserMessage, Integer> {
    List<UserMessage> findAllByTelegramUser_Id(Integer telegramUser_Id);
}
