package com.fineplay.fineplaybackend.auth.controller;

import com.fineplay.fineplaybackend.auth.dto.request.SignUpRequestDto;
import com.fineplay.fineplaybackend.auth.dto.response.SignUpResponseDto;
import com.fineplay.fineplaybackend.auth.service.AuthService;
import com.fineplay.fineplaybackend.dto.response.ErrorResponseDto;
import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/sign-up")
    public ResponseEntity<? super SignUpResponseDto> signUp(@RequestBody @Valid SignUpRequestDto requestBody, BindingResult bindingResult) {

//        if (bindingResult.hasErrors()) {
//            StringBuilder sb = new StringBuilder();
//            bindingResult.getAllErrors().forEach(objectError -> {
//
//                FieldError field = (FieldError) objectError;
//                String message = field.getDefaultMessage();
//                String fieldName = field.getField();
//                String rejectedValue = (String) field.getRejectedValue();
//
//                sb.append("message:" + message + "\n");
//                sb.append("field name:" + fieldName + "\n");
//                sb.append("rejected value: " + rejectedValue + "\n");
//            });
//
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(sb.toString());
//        }
        if (bindingResult.hasErrors()) {
            List<ErrorResponseDto.FieldError> errors = bindingResult.getFieldErrors().stream()
                    .map(error -> new ErrorResponseDto.FieldError(
                            error.getField(),
                            error.getRejectedValue(),
                            error.getDefaultMessage()))
                    .collect(Collectors.toList());

            ErrorResponseDto errorResponse = new ErrorResponseDto(errors);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
        return authService.signUp(requestBody);
    }
}
