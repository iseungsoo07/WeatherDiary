package com.zerobase.myweather.repository;

import com.zerobase.myweather.domain.Diary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiaryRepository extends JpaRepository<Diary, Integer> {
}
