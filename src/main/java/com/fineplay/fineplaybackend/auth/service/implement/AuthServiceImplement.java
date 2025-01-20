package com.fineplay.fineplaybackend.auth.service.implement;

import com.fineplay.fineplaybackend.auth.service.AuthService;
import com.fineplay.fineplaybackend.auth.dto.request.SignUpRequestDto;
import com.fineplay.fineplaybackend.dto.response.ResponseDto;
import com.fineplay.fineplaybackend.auth.dto.response.SignUpResponseDto;
import com.fineplay.fineplaybackend.auth.entity.UserEntity;
import com.fineplay.fineplaybackend.auth.controller.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImplement implements AuthService {

    private final UserRepository userRepository;
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public ResponseEntity<? super SignUpResponseDto> signUp(SignUpRequestDto dto) {

        try {
            // 이메일 중복 확인
            String email = dto.getEmail();
            boolean exsitedEmail = userRepository.existsByEmail(email);
            if (exsitedEmail) return SignUpResponseDto.duplicateEmail();

            // 닉네임 중복 확인
            String nickName = dto.getNickName();
            boolean exsitedNickName = userRepository.existsByNickName(nickName);
            if (exsitedNickName) return SignUpResponseDto.duplicateNickname();

            // 핸드폰 번호 중복 확인
            String phoneNumber = dto.getPhoneNumber();
            boolean exsitedPhoneNumber = userRepository.existsByPhoneNumber(phoneNumber);
            if (exsitedPhoneNumber) return SignUpResponseDto.duplicatePhoneNumber();

            // 비밀번호 암호화
            String password = dto.getPassword();
            String encodedPassword = passwordEncoder.encode(password);
            dto.setPassword(encodedPassword);

            UserEntity userEntity = new UserEntity(dto);
            userRepository.save(userEntity);


        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseDto.databaseError();
        }
        return SignUpResponseDto.success();
    }
}
