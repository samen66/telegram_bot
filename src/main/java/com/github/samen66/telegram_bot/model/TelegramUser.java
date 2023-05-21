package com.github.samen66.telegram_bot.model;

import jakarta.persistence.*;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@Data
@Entity
@Table(name = "telegram_users")
public class TelegramUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private Integer id;

    @Column(name = "chat_id")
    @NotNull
    private String chatId;

    @OneToMany(mappedBy = "telegramUser", fetch = FetchType.EAGER)
    private List<UserMessage> userMessageList;

    public TelegramUser() {

    }
}
