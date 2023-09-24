package com.zerobase.myweather;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@EnableScheduling
public class MyWeatherApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyWeatherApplication.class, args);
    }

}
