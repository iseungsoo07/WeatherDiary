package com.zerobase.myweather.service;

import com.zerobase.myweather.domain.DateWeather;
import com.zerobase.myweather.domain.Diary;
import com.zerobase.myweather.exception.DiaryException;
import com.zerobase.myweather.repository.DiaryRepository;
import com.zerobase.myweather.type.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class DiaryServiceImpl implements DiaryService {

    private final DiaryRepository diaryRepository;
    private final WeatherApiService weatherApiService;

    private static final Logger logger = LoggerFactory.getLogger(DiaryServiceImpl.class);

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void createDiary(LocalDate date, String text) {
        logger.info("started to create diary");
        DateWeather dateWeather = weatherApiService.getDateWeather(date);

        // DB에 저장
        Diary newDiary = new Diary();
        newDiary.setDateWeather(dateWeather);
        newDiary.setText(text);
        newDiary.setDate(date);

        diaryRepository.save(newDiary);
        logger.info("end to create diary");
    }


    @Override
    @Transactional(readOnly = true)
    public List<Diary> readDiary(LocalDate date) {
        logger.debug("read diary");
        if (date.isAfter(LocalDate.ofYearDay(3000, 1))) {
            throw new DiaryException(ErrorCode.TOO_FAR_IN_THE_FUTURE);
        }

        if (date.isBefore(LocalDate.ofYearDay(1800, 1))) {
            throw new DiaryException(ErrorCode.TOO_OLD_DATE);
        }

        return diaryRepository.findAllByDate(date);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Diary> readDiaries(LocalDate startDate, LocalDate endDate) {
        logger.info("{}와 {}사이의 일기 모두 가져오기",
                startDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                endDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        return diaryRepository.findAllByDateBetween(startDate, endDate);
    }

    @Override
    public void updateDiary(LocalDate date, String text) {
        logger.info("started update diary");
        Diary diary = diaryRepository.findFirstByDate(date);
        diary.updateText(text);
        diaryRepository.save(diary);
    }

    @Override
    public void deleteDiary(LocalDate date) {
        logger.info("{}에 작성된 일기 모두 삭제", date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        diaryRepository.deleteAllByDate(date);
    }


}
