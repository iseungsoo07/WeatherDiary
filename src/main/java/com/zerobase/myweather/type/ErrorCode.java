package com.zerobase.myweather.type;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum ErrorCode {
    TOO_OLD_DATE("너무 과거의 날짜입니다."),
    TOO_FAR_IN_THE_FUTURE("너무 미래의 날짜입니다.");

    private String description;
}
