package com.zerobase.myweather.exception;

import com.zerobase.myweather.type.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DiaryException extends RuntimeException {
    private final ErrorCode errorCode;
    private final String errorMessage;

    public DiaryException(ErrorCode errorCode) {
        this.errorCode = errorCode;
        this.errorMessage = errorCode.getDescription();
    }
}
