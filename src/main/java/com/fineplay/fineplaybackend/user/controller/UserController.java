package com.fineplay.fineplaybackend.user.controller;

import com.fineplay.fineplaybackend.user.dto.request.PatchNicknameRequestDto;
import com.fineplay.fineplaybackend.user.dto.request.PatchPositionRequestDto;
import com.fineplay.fineplaybackend.user.dto.response.GetSignInUserResponseDto;
import com.fineplay.fineplaybackend.user.dto.response.PatchNicknameResponseDto;
import com.fineplay.fineplaybackend.user.dto.response.PatchPositionResponseDto;
import com.fineplay.fineplaybackend.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 로그인한 유저 정보 가져오기
    @GetMapping("")
    public ResponseEntity<? super GetSignInUserResponseDto> getSignInUser(@AuthenticationPrincipal String email) {
        return userService.getSignInUser(email);
    }

    // 닉네임 수정하기
    @PatchMapping("/nickname")
    public ResponseEntity<? super PatchNicknameResponseDto> patchNickname(
            @RequestBody @Valid PatchNicknameRequestDto requestBody,
            @AuthenticationPrincipal String email
    ) {
            return userService.patchNickname(requestBody, email);
    }

    // 포지션 수정하기
    @PatchMapping("/position")
    public ResponseEntity<? super PatchPositionResponseDto> patchPosition(
            @RequestBody @Valid PatchPositionRequestDto requestBody,
            @AuthenticationPrincipal String email
    ) {
        return userService.patchPosition(requestBody, email);
    }
}
