package com.github.samen66.telegram_bot.service;

import com.github.samen66.telegram_bot.model.Currency;

import java.util.List;

public interface CurrencyService {
    void save(Currency currency);
    List<Currency> getAllCurrency();
    Currency findById(Integer id);
    Currency findByCode(String code);
}
