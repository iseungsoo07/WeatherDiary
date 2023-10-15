package com.zerobase.myweather.service;

import com.zerobase.myweather.repository.DateWeatherRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ScheduledService {

    private static final Logger logger = LoggerFactory.getLogger(ScheduledService.class);

    private final DateWeatherRepository dateWeatherRepository;
    private final WeatherApiService weatherApiService;

    @Scheduled(cron = "0 0 1 * * *")
    public void saveDateWeatherEvery1Am() {
        logger.info("오늘의 날씨 데이터 저장");
        dateWeatherRepository.save(weatherApiService.getWeatherFromApi());
    }
}
