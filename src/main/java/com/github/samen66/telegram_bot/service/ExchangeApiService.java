package com.github.samen66.telegram_bot.service;

import okhttp3.OkHttpClient;

import java.io.IOException;

public interface ExchangeApiService {
    String exchange(String form , String to, Integer amount) throws IOException;

}
