package com.fineplay.fineplaybackend.exception;

import com.fineplay.fineplaybackend.dto.response.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class BadRequestExceptionHandler {

    @ExceptionHandler({MethodArgumentNotValidException.class, HttpMessageNotReadableException.class})
    public ResponseEntity<ResponseDto> validationExceptionHandler(Exception exception) {
        return ResponseDto.validationFailed();
    }

    //Mypage관련 error
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Map<String, Object>> handleResponseStatusException(ResponseStatusException exception) {
        String reason = exception.getReason();
        Map<String, Object> errorResponse = new LinkedHashMap<>(); // ✅ 순서 보장됨

        if ("INVALID_USER_ID".equals(reason)) {
            errorResponse.put("errCode", "INVALID_USER_ID");
            errorResponse.put("statusCode", 400);
            errorResponse.put("responseMessage", "유효하지 않은 UserId");
        }
        else if ("INVALID_SELECTED_STAT".equals(reason)) {
            errorResponse.put("errCode", "INVALID_SELECTED_STAT");
            errorResponse.put("statusCode", 400);
            errorResponse.put("responseMessage", "선택된 스탯이 유효하지 않습니다.");
        }
        else if ("INVALID_STAT_NAME".equals(reason)) {
            errorResponse.put("errCode", "INVALID_STAT_NAME");
            errorResponse.put("statusCode", 400);
            errorResponse.put("responseMessage", "스탯 이름이 유효하지 않습니다.");
        }
        else if ("INVALID_USER_ID_MISMATCH".equals(reason)) {
            errorResponse.put("errCode", "INVALID_USER_ID_MISMATCH");
            errorResponse.put("statusCode", 400);
            errorResponse.put("responseMessage", "요청된 userId가 토큰 userId와 일치하지 않습니다(본인의 special stat만 변경가능).");
        }
        else {
            throw exception; // 다른 예외는 그대로 던짐
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
}
