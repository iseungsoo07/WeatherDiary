package com.zerobase.myweather.service;

import com.zerobase.myweather.domain.DateWeather;

import java.time.LocalDate;
import java.util.Map;

public interface WeatherApiService {
    DateWeather getWeatherFromApi();

    DateWeather getDateWeather(LocalDate date);

    String getWeatherString();

    Map<String, Object> parseWeather(String jsonString);
}
