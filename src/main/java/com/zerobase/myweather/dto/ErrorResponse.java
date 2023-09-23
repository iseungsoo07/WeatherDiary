package com.zerobase.myweather.dto;

import com.zerobase.myweather.type.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
    private ErrorCode errorCode;
    private String errorMessage;
}
