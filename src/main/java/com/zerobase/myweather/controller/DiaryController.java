package com.zerobase.myweather.controller;

import com.zerobase.myweather.service.DiaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

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

    @
}
