

package com.fineplay.fineplaybackend.setting.controller;

import com.fineplay.fineplaybackend.setting.dto.request.*;
import com.fineplay.fineplaybackend.setting.dto.response.*;
import com.fineplay.fineplaybackend.setting.service.intf.SettingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/setting")
public class SettingController {

    private final SettingService settingService;

    public SettingController(SettingService settingService) {
        this.settingService = settingService;
    }

    // ✅ 프로필 수정
    @PatchMapping("/AccountInfo/EditProfile")
    public ResponseEntity<? super EditProfileResponseDto> editProfile(@RequestBody EditProfileRequestDto request) {
        return settingService.updateProfile(request);
    }

    // ✅ 비밀번호 변경
    @PatchMapping("/AccountInfo/EditPassword")
    public ResponseEntity<? super EditPasswordResponseDto> editPassword(@RequestBody EditPasswordRequestDto request) {
        return settingService.updatePassword(request);
    }

    // ✅ 비밀번호 확인 (회원 탈퇴 시)
    @PostMapping("/AccountInfo/withdraw/CheckPassword")
    public ResponseEntity<? super PasswordCheckResponseDto> checkPassword(@RequestBody PasswordCheckRequestDto request) {
        return settingService.checkPassword(request);
    }

    // ✅ 회원 탈퇴
    @DeleteMapping("/AccountInfo/withdraw/WithdrawService")
    public ResponseEntity<? super EditProfileResponseDto> withdrawUser(@RequestParam String email) {
        return settingService.withdrawUser(email);
    }

    // ✅ 매치 알림 설정
    @PostMapping("/MatchAlarm")
    public ResponseEntity<? super AlarmResponseDto> updateMatchAlarm(@RequestBody AlarmRequestDto request) {
        return settingService.updateMatchAlarm(request);
    }

    // ✅ 커뮤니티 알림 설정
    @PostMapping("/CommunityAlarm")
    public ResponseEntity<? super AlarmResponseDto> updateCommunityAlarm(@RequestBody AlarmRequestDto request) {
        return settingService.updateCommunityAlarm(request);
    }

    // ✅ 공지사항 조회
    @GetMapping("/Notice")
    public ResponseEntity<? super NoticeResponseDto> getNotice() {
        return settingService.getNotice();
    }

    // ✅ 이용 약관 조회
    @GetMapping("/TermsOfUse")
    public ResponseEntity<? super TermsOfUseResponseDto> getTermsOfUse() {
        return settingService.getTermsOfUse();
    }

    // ✅ 버그 제보 및 문의하기 조회
    @GetMapping("/ReportBugs")
    public ResponseEntity<? super ReportBugsResponseDto> getReportBugs() {
        return settingService.getReportBugs();
    }
}
