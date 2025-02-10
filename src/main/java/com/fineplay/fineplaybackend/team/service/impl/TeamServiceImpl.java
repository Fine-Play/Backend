package com.fineplay.fineplaybackend.team.service.impl;

import com.fineplay.fineplaybackend.mypage.repository.UserProfileRepository;
import com.fineplay.fineplaybackend.mypage.entity.UserProfile;
import com.fineplay.fineplaybackend.team.dto.request.CreateTeamRequestDto;
import com.fineplay.fineplaybackend.team.dto.response.TeamCreationResponseDto;
import com.fineplay.fineplaybackend.team.dto.response.TeamListResponseDto;
import com.fineplay.fineplaybackend.team.entity.TeamEntity;
import com.fineplay.fineplaybackend.team.repository.TeamRepository;
import com.fineplay.fineplaybackend.team.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;


import com.fineplay.fineplaybackend.team.dto.response.TeamResponseDto;



import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class TeamServiceImpl implements TeamService {

    private final TeamRepository teamRepository;
    private final UserProfileRepository userProfileRepository;

    @Override
    public boolean isTeamNameAvailable(String teamName) {
        return !teamRepository.existsByTeamName(teamName);
    }

    @Override
    @Transactional
    public TeamCreationResponseDto createTeam(CreateTeamRequestDto requestDto) {
        // ✅ userId 존재 여부 확인
        Optional<UserProfile> userProfileOpt = userProfileRepository.findByUserId(requestDto.getUserId());
        if (userProfileOpt.isEmpty()) {
            return new TeamCreationResponseDto("USER_NOT_FOUND"); // ❌ 사용자 없음
        }

        // ✅ 팀 이름 중복 확인
        if (teamRepository.existsByTeamName(requestDto.getTeamName())) {
            return new TeamCreationResponseDto("DUPLICATE_TEAM_NAME"); // ❌ 중복된 팀 이름
        }

        // ✅ 팀 생성
        TeamEntity team = new TeamEntity(
                requestDto.getTeamName(),
                requestDto.getHomeTown1(),
                requestDto.getHomeTown2(),
                requestDto.getSports(),
                requestDto.getAutoAccept(),
                requestDto.getUserId()
        );
        team = teamRepository.save(team); // teamId 자동 생성됨

        // ✅ 5자리 숫자 포맷으로 변환
        Long createdTeamId = team.getTeamId();

        // ✅ User_Profile 테이블 업데이트 (team1 → team2 → team3 순서로 저장)
        userProfileOpt.ifPresent(userProfile -> {
            if (userProfile.getTeam1() == null) {
                userProfile.setTeam1(createdTeamId);
            } else if (userProfile.getTeam2() == null) {
                userProfile.setTeam2(createdTeamId);
            } else if (userProfile.getTeam3() == null) {
                userProfile.setTeam3(createdTeamId);
            } else {
                throw new RuntimeException("최대 3개의 팀만 가입할 수 있습니다.");
            }
            userProfileRepository.save(userProfile);
        });

        return new TeamCreationResponseDto(null); // ✅ 성공
    }

    @Override
    @Transactional(readOnly = true)
    public TeamListResponseDto getMyTeams(Long userId) {
        Optional<UserProfile> userProfileOptional = userProfileRepository.findByUserId(userId);

        if (userProfileOptional.isEmpty()) {
            return new TeamListResponseDto(404, "사용자를 찾을 수 없습니다.", new ArrayList<>());
        }

        UserProfile userProfile = userProfileOptional.get();
        List<TeamResponseDto> teamList = new ArrayList<>();

        // 팀 ID 리스트 가져오기
        List<Long> teamIds = new ArrayList<>();
        if (userProfile.getTeam1() != null) teamIds.add(userProfile.getTeam1());
        if (userProfile.getTeam2() != null) teamIds.add(userProfile.getTeam2());
        if (userProfile.getTeam3() != null) teamIds.add(userProfile.getTeam3());

        // 팀 정보 조회
        for (Long teamId : teamIds) {
            Optional<TeamEntity> teamEntityOptional = teamRepository.findById(teamId);
            teamEntityOptional.ifPresent(teamEntity -> {
                teamList.add(new TeamResponseDto(
                        teamEntity.getTeamName(),
                        "100", // OVR (임시 데이터)
                        "5", // Win (임시 데이터)
                        "3", // Draw (임시 데이터)
                        "3", // Lose (임시 데이터)
                        teamEntity.getHomeTown1(),
                        "12", // MemberNum (임시 데이터)
                        teamEntity.getSports()
                ));
            });
        }

        return new TeamListResponseDto(200, "팀 리스트", teamList);
    }
}
