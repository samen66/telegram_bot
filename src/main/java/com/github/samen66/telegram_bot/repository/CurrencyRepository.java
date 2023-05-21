package com.github.samen66.telegram_bot.repository;

import com.github.samen66.telegram_bot.model.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency, Integer> {
    Currency findFirstByCode(String code);
}
