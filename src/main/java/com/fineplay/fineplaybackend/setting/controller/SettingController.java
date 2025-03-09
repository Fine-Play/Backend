//package com.fineplay.fineplaybackend.setting.controller;
//
//import com.fineplay.fineplaybackend.auth.entity.UserEntity;
//import com.fineplay.fineplaybackend.auth.repository.UserRepository;
//import com.fineplay.fineplaybackend.provider.JwtProvider;
//import com.fineplay.fineplaybackend.setting.dto.request.*;
//import com.fineplay.fineplaybackend.setting.dto.response.*;
//import com.fineplay.fineplaybackend.setting.service.intf.SettingService;
//import jakarta.servlet.http.HttpServletRequest;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/api/setting")
//public class SettingController {
//
//    private final SettingService settingService;
//    private final JwtProvider jwtProvider;
//    private final UserRepository userRepository;
//
//    public SettingController(SettingService settingService, JwtProvider jwtProvider, UserRepository userRepository) {
//        this.settingService = settingService;
//        this.jwtProvider = jwtProvider;
//        this.userRepository = userRepository;
//    }
//
//    // ✅ 프로필 수정 (토큰 검증)
//    @PatchMapping("/AccountInfo/EditProfile")
//    public ResponseEntity<? super EditProfileResponseDto> editProfile(HttpServletRequest request, @RequestBody EditProfileRequestDto editRequest) {
//        UserEntity user = validateTokenAndGetUser(request);
//        if (user == null) {
//            return ResponseEntity.status(401).body(new EditProfileResponseDto("인증 실패: 유효하지 않은 토큰입니다.", "UNAUTHORIZED"));
//        }
//        return settingService.updateProfile(editRequest);
//    }
//
//    // ✅ 비밀번호 변경 (토큰 검증)
//    @PatchMapping("/AccountInfo/EditPassword")
//    public ResponseEntity<? super EditPasswordResponseDto> editPassword(HttpServletRequest request, @RequestBody EditPasswordRequestDto passwordRequest) {
//        UserEntity user = validateTokenAndGetUser(request);
//        if (user == null) {
//            return ResponseEntity.status(401).body(EditPasswordResponseDto.error("인증 실패: 유효하지 않은 토큰입니다.", "UNAUTHORIZED"));
//        }
//        return settingService.updatePassword(passwordRequest);
//    }
//
//    // ✅ 비밀번호 확인 (회원 탈퇴 시) (토큰 검증)
//    @PostMapping("/AccountInfo/withdraw/CheckPassword")
//    public ResponseEntity<? super PasswordCheckResponseDto> checkPassword(HttpServletRequest request, @RequestBody PasswordCheckRequestDto passwordRequest) {
//        UserEntity user = validateTokenAndGetUser(request);
//        if (user == null) {
//            return ResponseEntity.status(401).body(PasswordCheckResponseDto.error("인증 실패: 유효하지 않은 토큰입니다."));
//        }
//        return settingService.checkPassword(passwordRequest);
//    }
//
//    // ✅ 회원 탈퇴 (토큰 검증)
//    @DeleteMapping("/AccountInfo/withdraw/WithdrawService")
//    public ResponseEntity<? super EditProfileResponseDto> withdrawUser(HttpServletRequest request, @RequestParam String email) {
//        UserEntity user = validateTokenAndGetUser(request);
//        if (user == null || !user.getEmail().equals(email)) {
//            return ResponseEntity.status(401).body(new EditProfileResponseDto("인증 실패: 유효하지 않은 토큰입니다.", "UNAUTHORIZED"));
//        }
//        return settingService.withdrawUser(email);
//    }
//
//    // ✅ 매치 알림 설정 (토큰 검증)
//    @PostMapping("/MatchAlarm")
//    public ResponseEntity<? super AlarmResponseDto> updateMatchAlarm(HttpServletRequest request, @RequestBody AlarmRequestDto alarmRequest) {
//        UserEntity user = validateTokenAndGetUser(request);
//        if (user == null) {
//            return ResponseEntity.status(401).body(AlarmResponseDto.error("인증 실패: 유효하지 않은 토큰입니다."));
//        }
//        return settingService.updateMatchAlarm(alarmRequest);
//    }
//
//    // ✅ 커뮤니티 알림 설정 (토큰 검증)
//    @PostMapping("/CommunityAlarm")
//    public ResponseEntity<? super AlarmResponseDto> updateCommunityAlarm(HttpServletRequest request, @RequestBody AlarmRequestDto alarmRequest) {
//        UserEntity user = validateTokenAndGetUser(request);
//        if (user == null) {
//            return ResponseEntity.status(401).body(AlarmResponseDto.error("인증 실패: 유효하지 않은 토큰입니다."));
//        }
//        return settingService.updateCommunityAlarm(alarmRequest);
//    }
//
//    // ✅ 공지사항 조회 (토큰 검증 없음)
//    @GetMapping("/Notice")
//    public ResponseEntity<? super NoticeResponseDto> getNotice() {
//        return settingService.getNotice();
//    }
//
//    // ✅ 이용 약관 조회 (토큰 검증 없음)
//    @GetMapping("/TermsOfUse")
//    public ResponseEntity<? super TermsOfUseResponseDto> getTermsOfUse() {
//        return settingService.getTermsOfUse();
//    }
//
//    // ✅ 버그 제보 및 문의하기 조회 (토큰 검증 없음)
//    @GetMapping("/ReportBugs")
//    public ResponseEntity<? super ReportBugsResponseDto> getReportBugs() {
//        return settingService.getReportBugs();
//    }
//
//    // ✅ JWT 토큰 검증 메서드
//    private UserEntity validateTokenAndGetUser(HttpServletRequest request) {
//        String token = jwtProvider.getTokenFromRequest(request);
//        if (token == null) {
//            return null;
//        }
//
//        String email = jwtProvider.validateJwt(token);
//        if (email == null) {
//            return null;
//        }
//
//        return userRepository.findByEmail(email);
//    }
//}
