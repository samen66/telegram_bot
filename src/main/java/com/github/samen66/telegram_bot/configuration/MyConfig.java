package com.github.samen66.telegram_bot.configuration;

import com.github.samen66.telegram_bot.MyBot;
import com.pengrad.telegrambot.TelegramBot;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;

import java.util.concurrent.TimeUnit;

@Configuration
@PropertySource("classpath:application.properties")
public class MyConfig {
    @Value("${telegram_token}")
    private String token;
    @Bean
    public TelegramBot getBot(){
        return new TelegramBot(token);
    }

}
