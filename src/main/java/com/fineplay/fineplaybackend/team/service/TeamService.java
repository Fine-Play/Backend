package com.fineplay.fineplaybackend.team.service;

import com.fineplay.fineplaybackend.team.dto.request.CreateTeamRequestDto;
import com.fineplay.fineplaybackend.team.dto.response.TeamCreationResponseDto;
import com.fineplay.fineplaybackend.team.dto.response.TeamListResponseDto;
import com.fineplay.fineplaybackend.team.dto.response.TeamMemberListResponseDto;
import com.fineplay.fineplaybackend.team.dto.response.TeamRegisterManageResponseDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TeamService {
    boolean isTeamNameAvailable(String teamName);
    TeamCreationResponseDto createTeam(CreateTeamRequestDto requestDto, Long userId);
    TeamListResponseDto getMyTeams(Long userId);

    List<TeamRegisterManageResponseDto> getTeamJoinRequests(Long teamId, Long leaderId);
    @Transactional
    String requestJoinTeam(Long userId, Long teamId);

    @Transactional
    String acceptJoinRequest(Long teamId, Long userId, Long leaderId);

    @Transactional
    String rejectJoinRequest(Long teamId, Long userId, Long leaderId);

    List<TeamMemberListResponseDto> getTeamMembers(Long teamId);
}
