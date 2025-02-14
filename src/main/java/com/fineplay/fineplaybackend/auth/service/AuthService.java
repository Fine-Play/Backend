package com.fineplay.fineplaybackend.auth.service;

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
import org.springframework.http.ResponseEntity;

public interface AuthService {

    ResponseEntity<? super SignUpResponseDto> signUp(SignUpRequestDto dto);

    ResponseEntity<? super SignInResponseDto> signIn(SignInRequestDto dto);

    ResponseEntity<? super FindIdResponseDto> findId(FindIdRequestDto dto);

    ResponseEntity<? super FindAndResetPasswordResponseDto> findPasswordAndReset(FindAndResetPasswordRequestDto dto);

    ResponseEntity<? super SetNewPasswordResponseDto> setNewPassword(SetNewPasswordRequestDto dto);

}
