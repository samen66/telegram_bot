package com.github.samen66.telegram_bot.model;

import java.time.LocalDateTime;

public class Currency {
    private int id;
    private String code;
    private Double value;
    private LocalDateTime lastUpdatedAt;

    public Currency(String code, Double value, LocalDateTime lastUpdatedAt) {
        this.code = code;
        this.value = value;
        this.lastUpdatedAt = lastUpdatedAt;
    }

    public Currency() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public LocalDateTime getLastUpdatedAt() {
        return lastUpdatedAt;
    }

    public void setLastUpdatedAt(LocalDateTime lastUpdatedAt) {
        this.lastUpdatedAt = lastUpdatedAt;
    }

    @Override
    public String toString() {
        return "Currency{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", value=" + value +
                ", lastUpdatedAt=" + lastUpdatedAt +
                '}';
    }
}
