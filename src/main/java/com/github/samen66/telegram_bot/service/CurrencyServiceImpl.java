package com.github.samen66.telegram_bot.service;

import com.github.samen66.telegram_bot.model.Currency;
import com.github.samen66.telegram_bot.repository.CurrencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CurrencyServiceImpl implements CurrencyService {
    private final CurrencyRepository currencyRepository;
    @Autowired
    public CurrencyServiceImpl(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    @Override
    public void save(Currency currency) {
        currencyRepository.save(currency);
    }

    @Override
    public List<Currency> getAllCurrency() {
        return currencyRepository.findAll();
    }

    @Override
    public Currency findById(Integer id) {
        return currencyRepository.findById(id).orElse(null);
    }

    @Override
    public Currency findByCode(String code) {
        return currencyRepository.findFirstByCode(code);
    }
}
