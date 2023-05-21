package com.github.samen66.telegram_bot.service;

import com.github.samen66.telegram_bot.model.Currency;
import okhttp3.OkHttpClient;

import java.io.IOException;
import java.util.List;

public interface ExchangeApiService {
    String exchange(String form , String to, Integer amount) throws IOException;
    List<Currency> exchange(List<String> listCurrency);

}
