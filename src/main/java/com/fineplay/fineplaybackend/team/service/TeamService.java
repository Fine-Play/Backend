package com.fineplay.fineplaybackend.team.service;

import com.fineplay.fineplaybackend.team.dto.request.CreateTeamRequestDto;
import com.fineplay.fineplaybackend.team.dto.response.TeamCreationResponseDto;
import com.fineplay.fineplaybackend.team.dto.response.TeamListResponseDto;

public interface TeamService {
    boolean isTeamNameAvailable(String teamName);
    TeamCreationResponseDto createTeam(CreateTeamRequestDto requestDto);

    TeamListResponseDto getMyTeams(Long userId);
}
