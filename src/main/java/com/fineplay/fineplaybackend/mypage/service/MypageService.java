package com.fineplay.fineplaybackend.mypage.service;

import com.fineplay.fineplaybackend.mypage.dto.response.MypageProfileResponseDto;
import com.fineplay.fineplaybackend.mypage.dto.request.SelectedStatRequestDto;
import com.fineplay.fineplaybackend.mypage.dto.response.SelectedStatResponseDto;
import com.fineplay.fineplaybackend.mypage.dto.request.PageMoveRequestDto;
import com.fineplay.fineplaybackend.mypage.dto.response.PageMoveResponseDto;

public interface MypageService {
    MypageProfileResponseDto getMypageProfile(Long userId);
    SelectedStatResponseDto updateSelectedStat(Long tokenUserId,SelectedStatRequestDto requestDto);
    PageMoveResponseDto movePage(String statName, PageMoveRequestDto requestDto);
}
