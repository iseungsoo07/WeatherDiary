package com.zerobase.myweather.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Diary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String weather;

    private String icon;

    private double temperature;

    private String text;

    private LocalDate date;

    public void updateText(String text) {
        this.text = text;
    }

    public void setDateWeather(DateWeather dateWeather) {
        this.weather = dateWeather.getWeather();
        this.date = dateWeather.getDate();
        this.temperature = dateWeather.getTemperature();
        this.icon = dateWeather.getIcon();
    }
}
