package com.fineplay.fineplaybackend.user.service;

import com.fineplay.fineplaybackend.user.dto.request.PatchNicknameRequestDto;
import com.fineplay.fineplaybackend.user.dto.request.PatchPositionRequestDto;
import com.fineplay.fineplaybackend.user.dto.response.GetSignInUserResponseDto;
import com.fineplay.fineplaybackend.user.dto.response.PatchNicknameResponseDto;
import com.fineplay.fineplaybackend.user.dto.response.PatchPositionResponseDto;
import org.springframework.http.ResponseEntity;

public interface UserService {

    ResponseEntity<? super GetSignInUserResponseDto> getSignInUser(String email);
    ResponseEntity<? super PatchNicknameResponseDto> patchNickname(PatchNicknameRequestDto dto, String email);
    ResponseEntity<? super PatchPositionResponseDto> patchPosition(PatchPositionRequestDto dto, String email);

}
