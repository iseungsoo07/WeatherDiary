package com.zerobase.myweather.controller;

import com.zerobase.myweather.domain.Diary;
import com.zerobase.myweather.service.DiaryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.format.annotation.DateTimeFormat.ISO;

@RestController
@RequiredArgsConstructor
@Tag(name = "날씨 일기 프로젝트 controller", description = "날씨 일기 프로젝트 API Document")
public class DiaryController {

    private final DiaryService diaryService;


    @Operation(summary = "일기 생성 API", description = "날씨 일기를 생성합니다.")
    @PostMapping("/create/diary")
    public void createDiary(@Parameter(description = "조회할 날짜 (yyyy-MM-dd)", example = "2022-02-02")
                            @RequestParam @DateTimeFormat(iso = ISO.DATE) LocalDate date,
                            @RequestBody String text) {
        diaryService.createDiary(date, text);
    }

    @Operation(summary = "해당 날짜 일기 조회 API")
    @GetMapping("/read/diary")
    public List<Diary> readDiary(@Parameter(description = "조회할 날짜 (yyyy-MM-dd)", example = "2022-02-02")
                                 @RequestParam @DateTimeFormat(iso = ISO.DATE) LocalDate date) {
        return diaryService.readDiary(date);
    }

    @Operation(summary = "시작 날짜와 종료 날짜 사이의 일기 조회 API")
    @GetMapping("/read/diaries")
    public List<Diary> readDiaries(@Parameter(description = "시작 날짜 (yyyy-MM-dd)", example = "2022-03-01")
                                   @RequestParam @DateTimeFormat(iso = ISO.DATE) LocalDate startDate,
                                   @Parameter(description = "종료 날짜 (yyyy-MM-dd)", example = "2022-03-31")
                                   @RequestParam @DateTimeFormat(iso = ISO.DATE) LocalDate endDate) {
        return diaryService.readDiaries(startDate, endDate);
    }

    @Operation(summary = "해당 날짜 일기 수정 API")
    @PutMapping("/update/diary")
    public void updateDiary(@Parameter(description = "조회할 날짜 (yyyy-MM-dd)", example = "2022-02-02")
                            @RequestParam @DateTimeFormat(iso = ISO.DATE) LocalDate date,
                            @RequestBody String text) {
        diaryService.updateDiary(date, text);
    }

    @Operation(summary = "해당 날짜 모든 일기 삭제 API")
    @DeleteMapping("/delete/diary")
    public void deleteDiary(@Parameter(description = "조회할 날짜 (yyyy-MM-dd)", example = "2022-02-02")
                            @RequestParam @DateTimeFormat(iso = ISO.DATE) LocalDate date) {
        diaryService.deleteDiary(date);
    }
}
