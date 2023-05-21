package com.github.samen66.telegram_bot.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.samen66.telegram_bot.model.Currency;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
public class ExchangeApiServiceImpl implements ExchangeApiService {

    @Value("${apikey}")
    private String apiKey;
    @Value("${url.exchanger.api}")
    private StringBuilder url;

    private final ObjectMapper objectMapper;
    @Autowired
    public ExchangeApiServiceImpl(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }


    @Override
    public String exchange(String form, String to, Integer amount) {
        url.append(String.format("convert?to=%s&from=%s&amount=%d", to, form, amount));

        Response response = sendRequest(url.toString());
        Double result = (Double) getJsonObject("result", response);
        return String.valueOf(result);
    }

    @Override
    public List<Currency> exchange(List<String> listCurrency) {
        url.append("?apikey=").append(apiKey);
        url.append("&currencies=");
        listCurrency.forEach(name ->{
            if(name.equals(listCurrency.get(listCurrency.size()-1))){
                url.append(name);
            }else{
                url.append(name).append("%2C");
            }
        });
        System.out.println(url.toString());
        Response response = sendRequest(url.toString());

        try {
            return jsonToObject(response.body().string());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Response sendRequest(String url){
        OkHttpClient client = new OkHttpClient().newBuilder()
                .connectTimeout(1000, TimeUnit.MILLISECONDS)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = null;
        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return response;
    }

    private List<Currency> jsonToObject(String body){
        List<Currency> currencyList = new ArrayList<>();
        System.out.println(body);
        try {
            JSONObject jsonObject = new JSONObject(body);
            String time = jsonObject.getJSONObject("meta").getString("last_updated_at")
                    .replace("T", " ")
                    .replace("Z", "");
            System.out.println(time);
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime localDateTime = LocalDateTime.parse(time, dateTimeFormatter);
            JSONObject data = jsonObject.getJSONObject("data");
            JSONArray jsonArray = jsonObject.getJSONObject("data").names();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject currency = data.getJSONObject(jsonArray.get(i).toString());
                currencyList.add(new Currency(currency.getString("code"), currency.getDouble("value"), localDateTime));

            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        return currencyList;
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
