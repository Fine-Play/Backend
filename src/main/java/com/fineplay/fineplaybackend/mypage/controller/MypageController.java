package com.fineplay.fineplaybackend.mypage.controller;

import com.fineplay.fineplaybackend.mypage.dto.request.MypageProfileRequestDto;
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

    // ✅ 1. 마이페이지 정보 조회 (userId를 요청 Body에서 받음)
    @PostMapping
    public MypageProfileResponseDto getMypageProfile(HttpServletRequest request,
                                                     @RequestBody MypageProfileRequestDto requestDto) {
        validateToken(request); // JWT 유효성만 검증
        return mypageService.getMypageProfile(requestDto.getUserId());
    }

    // ✅ 2. 선택된 스탯 저장 (userId를 요청 Body에서 받음)
    @PostMapping("/selectedstat")
    public SelectedStatResponseDto updateSelectedStat(HttpServletRequest request,
                                                      @RequestBody SelectedStatRequestDto requestDto) {
        validateToken(request);
        Long tokenUserId = jwtProvider.getUserIdFromRequest(request);
        return mypageService.updateSelectedStat(tokenUserId, requestDto);
    }

    // ✅ 3. 특정 스탯 페이지 이동 (userId를 요청 Body에서 받음)
    @PostMapping("/page/{stat_name}")
    public PageMoveResponseDto movePage(@PathVariable("stat_name") String statName,
                                        HttpServletRequest request,
                                        @RequestBody PageMoveRequestDto requestDto) {
        validateToken(request);
        return mypageService.movePage(statName, requestDto);
    }

    // ✅ 기존 JwtProvider의 메서드를 활용하여 JWT 유효성만 검증
    private void validateToken(HttpServletRequest request) {
        String token = jwtProvider.getTokenFromRequest(request);
        if (token == null) {
            throw new RuntimeException("JWT Token not found in request");
        }
        jwtProvider.validateJwt(token); // ✅ JWT가 유효한지만 확인
    }
}