package com.zerobase.myweather.controller;

import com.zerobase.myweather.domain.Diary;
import com.zerobase.myweather.service.DiaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.format.annotation.DateTimeFormat.ISO;

@RestController
@RequiredArgsConstructor
public class DiaryController {

    private final DiaryService diaryService;

    @PostMapping("/create/diary")
    public void createDiary(@RequestParam @DateTimeFormat(iso = ISO.DATE) LocalDate date,
                            @RequestBody String text) {
        diaryService.createDiary(date, text);
    }

    @GetMapping("/read/diary")
    public List<Diary> readDiary(@RequestParam @DateTimeFormat(iso = ISO.DATE) LocalDate date) {
        return diaryService.readDiary(date);
    }
}
