package com.github.samen66.telegram_bot.service;

import com.github.samen66.telegram_bot.model.TelegramUser;
import com.github.samen66.telegram_bot.model.UserMessage;
import com.github.samen66.telegram_bot.repository.UserMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserMessageServiceImpl implements UserMessageService{
    private final UserMessageRepository userMessageRepository;

    @Autowired
    public UserMessageServiceImpl(UserMessageRepository userMessageService) {
        this.userMessageRepository = userMessageService;
    }


    @Override
    public List<UserMessage> getUserMessage(Integer userId) {
        return userMessageRepository.findAllByTelegramUser_Id(userId);
    }

    @Override
    public UserMessage getUserMessageById(Integer userId, Integer messageId) {
        Optional<UserMessage> userMessageOptional = userMessageRepository.findById(messageId);
        if (userMessageOptional.isEmpty()){
            return null;
        } else if (!userMessageOptional.get().getTelegramUser().getId().equals(userId)) {
            return null;
        }

        return userMessageOptional.get();
    }

    @Override
    public void save(UserMessage userMessage) {
        userMessageRepository.save(userMessage);
    }

    public void createNewMessage(String messageContent, TelegramUser user){
        UserMessage userMessage = new UserMessage();
        userMessage.setTextMessageContent(messageContent);
        userMessage.setRecivedTime(LocalDateTime.now());
        userMessage.setTelegramUser(user);
        save(userMessage);
    }

}
