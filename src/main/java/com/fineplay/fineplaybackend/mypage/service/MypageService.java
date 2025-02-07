package com.fineplay.fineplaybackend.mypage.service;

import com.fineplay.fineplaybackend.mypage.dto.MypageProfileResponseDto;
import com.fineplay.fineplaybackend.mypage.dto.SelectedStatRequestDto;
import com.fineplay.fineplaybackend.mypage.dto.SelectedStatResponseDto;
import com.fineplay.fineplaybackend.mypage.dto.PageMoveRequestDto;
import com.fineplay.fineplaybackend.mypage.dto.PageMoveResponseDto;

public interface MypageService {
    MypageProfileResponseDto getMypageProfile(Long userId);
    SelectedStatResponseDto updateSelectedStat(SelectedStatRequestDto requestDto);
    PageMoveResponseDto movePage(String statName, PageMoveRequestDto requestDto);
}
