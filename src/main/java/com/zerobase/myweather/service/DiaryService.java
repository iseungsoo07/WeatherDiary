package com.zerobase.myweather.service;

import com.zerobase.myweather.MyWeatherApplication;
import com.zerobase.myweather.domain.DateWeather;
import com.zerobase.myweather.domain.Diary;
import com.zerobase.myweather.exception.DiaryException;
import com.zerobase.myweather.repository.DateWeatherRepository;
import com.zerobase.myweather.repository.DiaryRepository;
import com.zerobase.myweather.type.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
public class DiaryService {

    private final DiaryRepository diaryRepository;
    private final DateWeatherRepository dateWeatherRepository;

    private static final Logger logger = LoggerFactory.getLogger(MyWeatherApplication.class);

    @Value("${openweathermap.key}")
    private String apiKey;

    @Scheduled(cron = "0 0 1 * * *")
    public void saveDateWeatherEvery1Am() {
        logger.info("오늘의 날씨 데이터 저장");
        dateWeatherRepository.save(getWeatherFromApi());
    }

    private DateWeather getWeatherFromApi() {
        String weatherString = getWeatherString();

        Map<String, Object> parsedWeather = parseWeather(weatherString);

        return DateWeather.builder()
                .date(LocalDate.now())
                .weather(parsedWeather.get("main").toString())
                .icon(parsedWeather.get("icon").toString())
                .temperature((Double) parsedWeather.get("temp"))
                .build();
    }

    private DateWeather getDateWeather(LocalDate date) {
        logger.info("날씨 정보 가져오기");
        List<DateWeather> dateWeatherListFromDB = dateWeatherRepository.findAllByDate(date);

        if (dateWeatherListFromDB.isEmpty()) {
            return getWeatherFromApi();
        } else {
            return dateWeatherListFromDB.get(0);
        }
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void createDiary(LocalDate date, String text) {
        logger.info("started to create diary");
        DateWeather dateWeather = getDateWeather(date);

        // DB에 저장
        Diary newDiary = new Diary();
        newDiary.setDateWeather(dateWeather);
        newDiary.setText(text);
        newDiary.setDate(date);

        diaryRepository.save(newDiary);
        logger.info("end to create diary");
    }


    @Transactional(readOnly = true)
    public List<Diary> readDiary(LocalDate date) {
        logger.debug("read diary");
        if (date.isAfter(LocalDate.ofYearDay(3000, 1))) {
            throw new DiaryException(ErrorCode.TOO_FAR_IN_THE_FUTURE);
        }

        if(date.isBefore(LocalDate.ofYearDay(1800, 1))) {
            throw new DiaryException(ErrorCode.TOO_OLD_DATE);
        }

        return diaryRepository.findAllByDate(date);
    }

    @Transactional(readOnly = true)
    public List<Diary> readDiaries(LocalDate startDate, LocalDate endDate) {
        logger.info("{}와 {}사이의 일기 모두 가져오기",
                startDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                endDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        return diaryRepository.findAllByDateBetween(startDate, endDate);
    }

    public void updateDiary(LocalDate date, String text) {
        logger.info("started update diary");
        Diary diary = diaryRepository.findFirstByDate(date);
        diary.updateText(text);
        diaryRepository.save(diary);
    }

    public void deleteDiary(LocalDate date) {
        logger.info("{}에 작성된 일기 모두 삭제", date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        diaryRepository.deleteAllByDate(date);
    }

    private String getWeatherString() {
        logger.info("open api 날씨 정보 가져오기");
        String apiUrl = "https://api.openweathermap.org/data/2.5/weather?q=seoul&appid=" + apiKey;

        try {
            URL url = new URL(apiUrl);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            int responseCode = connection.getResponseCode();

            BufferedReader br;

            if (responseCode == 200) {
                br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            } else {
                br = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
            }

            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }

            br.close();

            return response.toString();
        } catch (Exception e) {
            return "failed to get response";
        }
    }

    private Map<String, Object> parseWeather(String jsonString) {
        logger.info("open api 날씨 정보를 json 형태로 파싱하기");
        // 받아온 날씨 데이터를 파싱
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject;

        try {
            jsonObject = (JSONObject) jsonParser.parse(jsonString);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        Map<String, Object> resultMap = new HashMap<>();

        JSONObject mainData = (JSONObject) jsonObject.get("main");
        resultMap.put("temp", mainData.get("temp"));

        JSONArray weatherArray = (JSONArray) jsonObject.get("weather");
        JSONObject weatherData = (JSONObject) weatherArray.get(0);

        resultMap.put("main", weatherData.get("main"));
        resultMap.put("icon", weatherData.get("icon"));

        return resultMap;
    }


}
