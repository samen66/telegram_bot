package com.github.samen66.telegram_bot.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pengrad.telegrambot.TelegramBot;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@PropertySource("classpath:application.properties")
@EnableTransactionManagement
public class MyConfig {
    @Value("${telegram_token}")
    private String token;

    @Bean
    public TelegramBot getBot(){
        return new TelegramBot(token);
    }

    @Bean
    public ObjectMapper getObjectMapper(){
        return new ObjectMapper();
    }

}
