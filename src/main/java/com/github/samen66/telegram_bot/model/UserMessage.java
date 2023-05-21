package com.github.samen66.telegram_bot.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "message")
public class UserMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id", nullable = false)
    private int message_id;
    @Column(name = "text_message_content")
    private String TextMessageContent;
    @Column(name = "recived_time")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime recivedTime;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private TelegramUser telegramUser;


}
