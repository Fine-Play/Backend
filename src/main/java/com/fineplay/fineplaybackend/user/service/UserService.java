package com.fineplay.fineplaybackend.user.service;

import com.fineplay.fineplaybackend.user.dto.response.GetSignInUserResponseDto;
import org.springframework.http.ResponseEntity;

public interface UserService {

    ResponseEntity<? super GetSignInUserResponseDto> getSignInUser(String email);
}
