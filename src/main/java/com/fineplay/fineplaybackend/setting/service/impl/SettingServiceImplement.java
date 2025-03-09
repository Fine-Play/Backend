//package com.fineplay.fineplaybackend.setting.service.impl;
//
//import com.fineplay.fineplaybackend.auth.entity.UserEntity;
//import com.fineplay.fineplaybackend.auth.repository.UserRepository;
//import com.fineplay.fineplaybackend.setting.dto.request.*;
//import com.fineplay.fineplaybackend.setting.dto.response.*;
//import com.fineplay.fineplaybackend.setting.entity.SettingEntity;
//import com.fineplay.fineplaybackend.setting.repository.SettingRepository;
//import com.fineplay.fineplaybackend.setting.service.intf.SettingService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDate;
//import java.time.format.DateTimeFormatter;
//import java.time.format.DateTimeParseException;
//import java.sql.Date;
//
//@Service
//@RequiredArgsConstructor
//public class SettingServiceImplement implements SettingService {
//
//    private final UserRepository userRepository;
//    private final SettingRepository settingRepository;
//    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//
//    // ✅ 프로필 수정
//    @Override
//    public ResponseEntity<? super EditProfileResponseDto> updateProfile(EditProfileRequestDto request) {
//        UserEntity user = userRepository.findByEmail(request.getEmail());
//        if (user == null) {
//            return ResponseEntity.badRequest().body(new EditProfileResponseDto("해당 이메일의 사용자를 찾을 수 없습니다.", "USER_NOT_FOUND"));
//        }
//
//        try {
//            LocalDate birthLocalDate = LocalDate.parse(request.getBirth(), formatter);
//            user.setBirth(Date.valueOf(birthLocalDate));
//        } catch (DateTimeParseException e) {
//            return ResponseEntity.badRequest().body(new EditProfileResponseDto("날짜 형식이 올바르지 않습니다. (예: yyyy-MM-dd)", "INVALID_DATE_FORMAT"));
//        }
//
//        user.setNickname(request.getNickname());
//        user.setPosition(request.getPosition());
//
//        userRepository.save(user);
//        return ResponseEntity.ok(new EditProfileResponseDto("프로필이 성공적으로 수정되었습니다.", null));
//    }
//
//    // ✅ 비밀번호 변경
//    @Override
//    public ResponseEntity<? super EditPasswordResponseDto> updatePassword(EditPasswordRequestDto request) {
//        UserEntity user = userRepository.findByEmail(request.getEmail());
//        if (user == null) {
//            return ResponseEntity.badRequest().body(EditPasswordResponseDto.error("해당 이메일의 사용자를 찾을 수 없습니다.", "USER_NOT_FOUND"));
//        }
//
//        if (!user.getPassword().equals(request.getOldPassword())) {
//            return ResponseEntity.badRequest().body(EditPasswordResponseDto.error("기존 비밀번호가 일치하지 않습니다.", "INCORRECT_PASSWORD"));
//        }
//
//        user.setPassword(request.getNewPassword());
//        userRepository.save(user);
//
//        return ResponseEntity.ok(EditPasswordResponseDto.success());
//    }
//
//    // ✅ 비밀번호 확인 (회원 탈퇴 시)
//    @Override
//    public ResponseEntity<? super PasswordCheckResponseDto> checkPassword(PasswordCheckRequestDto request) {
//        UserEntity user = userRepository.findByEmail(request.getEmail());
//        if (user == null) {
//            return ResponseEntity.badRequest().body(PasswordCheckResponseDto.error("USER_NOT_FOUND"));
//        }
//
//        boolean isCorrect = user.getPassword().equals(request.getPassword());
//        return ResponseEntity.ok(new PasswordCheckResponseDto(isCorrect, isCorrect ? null : "INCORRECT_PASSWORD"));
//    }
//
//    // ✅ 회원 탈퇴
//    @Override
//    public ResponseEntity<? super EditProfileResponseDto> withdrawUser(String email) {
//        UserEntity user = userRepository.findByEmail(email);
//        if (user == null) {
//            return ResponseEntity.badRequest().body(new EditProfileResponseDto("해당 이메일의 사용자를 찾을 수 없습니다.", "USER_NOT_FOUND"));
//        }
//
//        userRepository.delete(user);
//        return ResponseEntity.ok(new EditProfileResponseDto("회원 탈퇴 완료", null));
//    }
//
//    // ✅ 매치 알림 업데이트
//    @Override
//    public ResponseEntity<? super AlarmResponseDto> updateMatchAlarm(AlarmRequestDto request) {
//        return updateAlarmSetting(request, true);
//    }
//
//    // ✅ 커뮤니티 알림 업데이트
//    @Override
//    public ResponseEntity<? super AlarmResponseDto> updateCommunityAlarm(AlarmRequestDto request) {
//        return updateAlarmSetting(request, false);
//    }
//
//    // 공통적인 알림 업데이트 로직을 별도 메서드로 처리
//    private ResponseEntity<? super AlarmResponseDto> updateAlarmSetting(AlarmRequestDto request, boolean isMatchAlarm) {
//        UserEntity user = userRepository.findByEmail(request.getEmail());
//        if (user == null) {
//            return ResponseEntity.badRequest().body(AlarmResponseDto.error("USER_NOT_FOUND"));
//        }
//
//        SettingEntity setting = settingRepository.findByUser(user);
//        if (setting == null) {
//            setting = new SettingEntity();
//            setting.setUser(user);
//        }
//
//        if (isMatchAlarm) {
//            setting.setMatchAlarm(request.isMatchAlarm());
//        } else {
//            setting.setCommunityAlarm(request.isCommunityAlarm());
//        }
//
//        settingRepository.save(setting);
//        return ResponseEntity.ok(AlarmResponseDto.success());
//    }
//
//    // ✅ 공지사항 조회
//    @Override
//    public ResponseEntity<? super NoticeResponseDto> getNotice() {
//        return ResponseEntity.ok(new NoticeResponseDto("공지사항 내용", "공지", null));
//    }
//
//    // ✅ 이용 약관 조회
//    @Override
//    public ResponseEntity<? super TermsOfUseResponseDto> getTermsOfUse() {
//        return ResponseEntity.ok(new TermsOfUseResponseDto("이용 약관 내용", "이용 약관", null));
//    }
//
//    // ✅ 버그 제보 및 문의하기 조회
//    @Override
//    public ResponseEntity<? super ReportBugsResponseDto> getReportBugs() {
//        return ResponseEntity.ok(new ReportBugsResponseDto("버그 제보 및 문의하기 내용", "버그 제보 및 문의하기", null));
//    }
//}
