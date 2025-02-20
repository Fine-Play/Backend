package com.fineplay.fineplaybackend.auth.service.implement;

import com.fineplay.fineplaybackend.auth.dto.request.FindIdRequestDto;
import com.fineplay.fineplaybackend.auth.dto.request.FindAndResetPasswordRequestDto;
import com.fineplay.fineplaybackend.auth.dto.request.SetNewPasswordRequestDto;
import com.fineplay.fineplaybackend.auth.dto.request.SignInRequestDto;
import com.fineplay.fineplaybackend.auth.dto.response.FindAndResetPasswordResponseDto;
import com.fineplay.fineplaybackend.auth.dto.response.FindIdResponseDto;
import com.fineplay.fineplaybackend.auth.dto.response.SetNewPasswordResponseDto;
import com.fineplay.fineplaybackend.auth.dto.response.SignInResponseDto;
import com.fineplay.fineplaybackend.auth.service.AuthService;
import com.fineplay.fineplaybackend.auth.dto.request.SignUpRequestDto;
import com.fineplay.fineplaybackend.dto.response.ResponseDto;
import com.fineplay.fineplaybackend.auth.dto.response.SignUpResponseDto;
import com.fineplay.fineplaybackend.auth.entity.UserEntity;
import com.fineplay.fineplaybackend.auth.repository.UserRepository;
import com.fineplay.fineplaybackend.mypage.entity.UserProfile;
import com.fineplay.fineplaybackend.mypage.repository.UserProfileRepository;
import com.fineplay.fineplaybackend.provider.JwtProvider;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImplement implements AuthService {

    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    private final UserProfileRepository userProfileRepository;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public ResponseEntity<? super SignUpResponseDto> signUp(SignUpRequestDto dto) {

        try {
            // 이메일 중복 확인
            String email = dto.getEmail();
            boolean existedEmail = userRepository.existsByEmail(email);
            if (existedEmail) return SignUpResponseDto.duplicateEmail();

            // 닉네임 중복 확인
            String nickName = dto.getNickName();
            boolean existedNickName = userRepository.existsByNickName(nickName);
            if (existedNickName) return SignUpResponseDto.duplicateNickname();

            // 핸드폰 번호 중복 확인
            String phoneNumber = dto.getPhoneNumber();
            boolean existedPhoneNumber = userRepository.existsByPhoneNumber(phoneNumber);
            if (existedPhoneNumber) return SignUpResponseDto.duplicatePhoneNumber();

            // 비밀번호 암호화
            String password = dto.getPassword();
            String encodedPassword = passwordEncoder.encode(password);
            dto.setPassword(encodedPassword);

            // ✅ UserEntity 저장
            UserEntity userEntity = new UserEntity(dto);
            userRepository.save(userEntity);

            // ✅ UserProfile 자동 생성 (회원가입 후)
            UserProfile userProfile = UserProfile.builder()
                    .userId(userEntity.getUserId())  // 자동 생성된 userId 가져오기
                    .nickName(userEntity.getNickName()) // 닉네임 설정
                    .team1(null)  // 기본적으로 팀 없음
                    .team2(null)
                    .team3(null)
                    .selectedTeam(null)
                    .build();
            userProfileRepository.save(userProfile);  // `UserProfile` 저장

        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseDto.databaseError();
        }
        return SignUpResponseDto.success();
    }

    @Override
    public ResponseEntity<? super SignInResponseDto> signIn(SignInRequestDto dto) {

        String accessToken = null;
        String refreshToken = null;

        try {
            // 유저 확인
            String email = dto.getEmail();
            UserEntity userEntity = userRepository.findByEmail(email);
            if (userEntity == null) return SignInResponseDto.signInFail(); // 일치하는 유저가 없음

            String password = dto.getPassword(); // 사용자가 입력한 비밀번호 값
            String encodedPassword = userEntity.getPassword(); // DB에 인코딩되어 저장된 값
            boolean isMatched = passwordEncoder.matches(password, encodedPassword);
            if (!isMatched) return SignInResponseDto.signInFail();

            // 토큰 생성
            accessToken = jwtProvider.createAccessToken(email);
            refreshToken = jwtProvider.createRefreshToken();

        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseDto.databaseError();
        }
        return SignInResponseDto.success(accessToken, refreshToken);
    }

    @Override
    public ResponseEntity<? super FindIdResponseDto> findId(FindIdRequestDto dto) {
        try {
            UserEntity userEntity = userRepository.findByRealNameAndPhoneNumberAndBirth(
                    dto.getRealName(),
                    dto.getPhoneNumber(),
                    dto.getBirth()
            );

            if (userEntity == null) return FindIdResponseDto.notExistUser();

            return FindIdResponseDto.success(userEntity.getEmail());

        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseDto.databaseError();
        }
    }

    @Override
    public ResponseEntity<? super FindAndResetPasswordResponseDto> findPasswordAndReset(FindAndResetPasswordRequestDto dto) {
        try {
            UserEntity userEntity = userRepository.findByRealNameAndEmailAndPhoneNumberAndBirth(
                    dto.getRealName(),
                    dto.getEmail(),
                    dto.getPhoneNumber(),
                    dto.getBirth()
            );

            if (userEntity == null) return FindAndResetPasswordResponseDto.notExistUser();

            // 비밀번호 초기화 - null로 설정
            userEntity.setPassword(null);
            userRepository.save(userEntity);

            return FindAndResetPasswordResponseDto.success();

        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseDto.databaseError();
        }
    }

    @Override
    public ResponseEntity<? super SetNewPasswordResponseDto> setNewPassword(SetNewPasswordRequestDto dto) {
        try {
            UserEntity userEntity = userRepository.findByEmail(dto.getEmail());
            if (userEntity == null) return SetNewPasswordResponseDto.notExistUser();

            // 비밀번호가 초기화되지 않은 경우 (보안 강화)
            if (userEntity.getPassword() != null) return SetNewPasswordResponseDto.notInitialized();

            // 새 비밀번호 설정
            userEntity.setPassword(passwordEncoder.encode(dto.getNewPassword()));
            userRepository.save(userEntity);

            return SetNewPasswordResponseDto.success();

        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseDto.databaseError();
        }
    }

}
