package com.fineplay.fineplaybackend.auth.service;

import com.fineplay.fineplaybackend.auth.dto.request.SignInRequestDto;
import com.fineplay.fineplaybackend.auth.dto.request.SignUpRequestDto;
import com.fineplay.fineplaybackend.auth.dto.response.SignInResponseDto;
import com.fineplay.fineplaybackend.auth.dto.response.SignUpResponseDto;
import org.springframework.http.ResponseEntity;

public interface AuthService {

    ResponseEntity<? super SignUpResponseDto> signUp(SignUpRequestDto dto);

    ResponseEntity<? super SignInResponseDto> signIn(SignInRequestDto dto);

}
