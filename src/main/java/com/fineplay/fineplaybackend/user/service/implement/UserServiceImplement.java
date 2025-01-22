package com.fineplay.fineplaybackend.user.service.implement;

import com.fineplay.fineplaybackend.auth.entity.UserEntity;
import com.fineplay.fineplaybackend.dto.response.ResponseDto;
import com.fineplay.fineplaybackend.user.dto.response.GetSignInUserResponseDto;
import com.fineplay.fineplaybackend.user.service.UserService;
import com.fineplay.fineplaybackend.auth.controller.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImplement implements UserService {

    private final UserRepository userRepository;

    @Override
    public ResponseEntity<? super GetSignInUserResponseDto> getSignInUser(String email) {
        UserEntity userEntity = null;

        try {
            userEntity = userRepository.findByEmail(email);
            if (userEntity == null) return GetSignInUserResponseDto.notExistUser();
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseDto.databaseError();
        }
        return GetSignInUserResponseDto.success(userEntity);
    }
}
