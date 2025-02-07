package com.fineplay.fineplaybackend.mypage.controller;

import com.fineplay.fineplaybackend.mypage.dto.*;
import com.fineplay.fineplaybackend.mypage.service.MypageService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/mypage")
public class MypageController {

    private final MypageService mypageService;

    @Autowired
    public MypageController(MypageService mypageService) {
        this.mypageService = mypageService;
    }

    // 1. 마이페이지 정보 조회 (GET /api/mypage?userId=...)
    @GetMapping
    public MypageProfileResponseDto getMypageProfile(@RequestParam Long userId) {
        return mypageService.getMypageProfile(userId);
    }
    // ✅ 선택된 스탯 저장 API (UserId + SelectedStat 함께 받음)
    @PostMapping("/selectedstat")
    public SelectedStatResponseDto updateSelectedStat(@Valid @RequestBody SelectedStatRequestDto requestDto) {
        return mypageService.updateSelectedStat(requestDto);
    }

    @PostMapping("/page/{stat_name}")
    public PageMoveResponseDto movePage(@PathVariable("stat_name") String statName,
                                        @RequestBody PageMoveRequestDto requestDto) {
        return mypageService.movePage(statName, requestDto);
    }
}
