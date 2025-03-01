package com.fineplay.fineplaybackend.auth.controller;

import com.fineplay.fineplaybackend.auth.dto.request.FindIdRequestDto;
import com.fineplay.fineplaybackend.auth.dto.request.FindAndResetPasswordRequestDto;
import com.fineplay.fineplaybackend.auth.dto.request.SetNewPasswordRequestDto;
import com.fineplay.fineplaybackend.auth.dto.request.SignInRequestDto;
import com.fineplay.fineplaybackend.auth.dto.request.SignUpRequestDto;
import com.fineplay.fineplaybackend.auth.dto.response.FindAndResetPasswordResponseDto;
import com.fineplay.fineplaybackend.auth.dto.response.FindIdResponseDto;
import com.fineplay.fineplaybackend.auth.dto.response.SetNewPasswordResponseDto;
import com.fineplay.fineplaybackend.auth.dto.response.SignInResponseDto;
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

    @PostMapping("/sign-in")
    public ResponseEntity<? super SignInResponseDto> signIn(@RequestBody @Valid SignInRequestDto requestBody) {
        return authService.signIn(requestBody);
    }

    // 아이디 찾기
    @PostMapping("/find-id")
    public ResponseEntity<? super FindIdResponseDto> findId(@RequestBody @Valid FindIdRequestDto requestBody) {
        return authService.findId(requestBody);
    }

    // 비밀번호 찾기 -> 초기화
    @PostMapping("/find-password")
    public ResponseEntity<? super FindAndResetPasswordResponseDto> findPasswordAndReset(@RequestBody @Valid FindAndResetPasswordRequestDto requestBody) {
        return authService.findPasswordAndReset(requestBody);
    }

    // 비밀번호 재설정 - 비밀번호가 null 상태일 때만 새 비밀번호 설정 가능
    @PostMapping("/set-new-password")
    public ResponseEntity<? super SetNewPasswordResponseDto> setNewPassword(@RequestBody @Valid SetNewPasswordRequestDto requestBody) {
        return authService.setNewPassword(requestBody);
    }

}
