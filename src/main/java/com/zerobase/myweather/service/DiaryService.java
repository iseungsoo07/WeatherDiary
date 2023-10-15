package com.zerobase.myweather.service;

import com.zerobase.myweather.domain.Diary;

import java.time.LocalDate;
import java.util.List;

public interface DiaryService {
    void createDiary(LocalDate date, String text);

    List<Diary> readDiary(LocalDate date);

    List<Diary> readDiaries(LocalDate startDate, LocalDate endDate);

    void updateDiary(LocalDate date, String text);

    void deleteDiary(LocalDate date);
}
