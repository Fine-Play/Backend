package com.fineplay.fineplaybackend.mypage.controller;

import com.fineplay.fineplaybackend.mypage.dto.response.MypageProfileResponseDto;
import com.fineplay.fineplaybackend.mypage.dto.request.SelectedStatRequestDto;
import com.fineplay.fineplaybackend.mypage.dto.response.SelectedStatResponseDto;
import com.fineplay.fineplaybackend.mypage.dto.request.PageMoveRequestDto;
import com.fineplay.fineplaybackend.mypage.dto.response.PageMoveResponseDto;
import com.fineplay.fineplaybackend.mypage.service.MypageService;
import com.fineplay.fineplaybackend.provider.JwtProvider;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/mypage")
public class MypageController {

    private final MypageService mypageService;
    private final JwtProvider jwtProvider;

    @Autowired
    public MypageController(MypageService mypageService, JwtProvider jwtProvider) {
        this.mypageService = mypageService;
        this.jwtProvider = jwtProvider;
    }

    // ✅ 1. 마이페이지 정보 조회 (JWT 검증 추가)
    @GetMapping
    public MypageProfileResponseDto getMypageProfile(HttpServletRequest request) {
        Long userId = jwtProvider.getUserIdFromRequest(request); // JWT 검증 및 userId 추출
        return mypageService.getMypageProfile(userId);
    }

    // ✅ 2. 선택된 스탯 저장 (JWT 검증 추가)
    @PostMapping("/selectedstat")
    public SelectedStatResponseDto updateSelectedStat(HttpServletRequest request,
                                                      @RequestBody SelectedStatRequestDto requestDto) {
        Long userId = jwtProvider.getUserIdFromRequest(request);
        requestDto.setUserId(userId); // 추출된 userId를 DTO에 설정
        return mypageService.updateSelectedStat(requestDto);
    }

    // ✅ 3. 특정 스탯 페이지 이동 (JWT 검증 추가)
    @PostMapping("/page/{stat_name}")
    public PageMoveResponseDto movePage(@PathVariable("stat_name") String statName,
                                        HttpServletRequest request,
                                        @RequestBody PageMoveRequestDto requestDto) {
        Long userId = jwtProvider.getUserIdFromRequest(request);
        requestDto.setUserId(userId);
        return mypageService.movePage(statName, requestDto);
    }
}