package com.github.samen66.telegram_bot.service;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
public class ExchangeApiServiceImpl implements ExchangeApiService {

    @Value("${apikey}")
    private String apiKey;
    @Value("${url.exchanger.api}")
    private StringBuilder url;


    @Override
    public String exchange(String form, String to, Integer amount) {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .connectTimeout(1000, TimeUnit.MILLISECONDS)
                .build();
        url.append(String.format("convert?to=%s&from=%s&amount=%d", to, form, amount));
        Request request = new Request.Builder()
                .url(url.toString())
                .addHeader("apikey", apiKey)
                .build();

        Response response = null;
        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        Double result = (Double) getJsonObject("result", response);

        return String.valueOf(result);
    }

    private Object getJsonObject(String objectName, Response response){
        System.out.println("ok");
        String jsonData = null;
        try {
            jsonData = Objects.requireNonNull(response.body()).string();
            JSONObject jsonObject = new JSONObject(jsonData);
            return jsonObject.get(objectName);
        } catch (IOException e) {
        } catch (JSONException e) {
        }
        return null;
    }
}
