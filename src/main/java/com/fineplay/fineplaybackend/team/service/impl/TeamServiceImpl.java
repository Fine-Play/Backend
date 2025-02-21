package com.fineplay.fineplaybackend.team.service.impl;

import com.fineplay.fineplaybackend.auth.entity.UserEntity;
import com.fineplay.fineplaybackend.mypage.entity.UserStat;
import com.fineplay.fineplaybackend.mypage.repository.UserProfileRepository;
import com.fineplay.fineplaybackend.mypage.entity.UserProfile;
import com.fineplay.fineplaybackend.mypage.repository.UserStatRepository;
import com.fineplay.fineplaybackend.team.dto.request.CreateTeamRequestDto;
import com.fineplay.fineplaybackend.team.dto.request.TeamUpdateRequestDto;
import com.fineplay.fineplaybackend.team.dto.response.*;
import com.fineplay.fineplaybackend.team.entity.TeamEntity;
import com.fineplay.fineplaybackend.team.entity.TeamJoinRequestEntity;
import com.fineplay.fineplaybackend.team.repository.TeamJoinRequestRepository;
import com.fineplay.fineplaybackend.team.repository.TeamRepository;
import com.fineplay.fineplaybackend.team.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

import com.fineplay.fineplaybackend.team.dto.response.TeamMemberListResponseDto;

import java.util.List;

import java.util.stream.Collectors;

import java.util.ArrayList;


@Service
@RequiredArgsConstructor
public class TeamServiceImpl implements TeamService {

    private final TeamRepository teamRepository;
    private final com.fineplay.fineplaybackend.auth.repository.UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;
    private final UserStatRepository userStatRepository;
    private final TeamJoinRequestRepository teamJoinRequestRepository;

    @Override
    public boolean isTeamNameAvailable(String teamName) {
        return !teamRepository.existsByTeamName(teamName);
    }

    @Override
    @Transactional
    public TeamCreationResponseDto createTeam(CreateTeamRequestDto requestDto, Long userId) {
        // ✅ userId 존재 여부 확인
        Optional<UserProfile> userProfileOpt = userProfileRepository.findByUserId(userId);
        if (userProfileOpt.isEmpty()) {
            return new TeamCreationResponseDto("USER_NOT_FOUND");
        }

        if (userId == null) {
            throw new RuntimeException("🚨 오류: userId가 null입니다. JWT에서 올바른 값이 전달되지 않았는지 확인하세요.");
        }

        // ✅ 팀 이름 중복 확인
        if (teamRepository.existsByTeamName(requestDto.getTeamName())) {
            return new TeamCreationResponseDto("DUPLICATE_TEAM_NAME");
        }

        // ✅ 팀 생성
        TeamEntity team = new TeamEntity(
                requestDto.getTeamName(),
                requestDto.getHomeTown1(),
                requestDto.getHomeTown2(),
                requestDto.getSports(),
                false,
                userId // JWT에서 받은 userId 사용
        );

        System.out.println("✅ [팀 생성] creatorUserId 값: " + userId); // 🚀 로그 추가
        team = teamRepository.save(team);
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

    /**
     * ✅ 팀 가입 신청
     */
    @Override
    @Transactional
    public String requestJoinTeam(Long userId, Long teamId) {
        Optional<UserProfile> userProfileOpt = userProfileRepository.findByUserId(userId);
        if (userProfileOpt.isEmpty()) return "USER_NOT_FOUND";

        Optional<TeamEntity> teamOpt = teamRepository.findById(teamId);
        if (teamOpt.isEmpty()) return "TEAM_NOT_FOUND";

        if (teamJoinRequestRepository.existsByTeamIdAndUserId(teamId, userId)) {
            return "ALREADY_REQUESTED";
        }
        // 이미 가입한 팀인지 확인
        UserProfile userProfile = userProfileOpt.get();
        if (teamId.equals(userProfile.getTeam1()) ||
                teamId.equals(userProfile.getTeam2()) ||
                teamId.equals(userProfile.getTeam3())) {
            return "ALREADY_IN_TEAM";
        }

        // 가입 신청 저장
        TeamJoinRequestEntity joinRequest = new TeamJoinRequestEntity(userId, teamId);
        teamJoinRequestRepository.save(joinRequest);
        return "JOIN_REQUEST_SUBMITTED";
    }

    @Override
    @Transactional(readOnly = true)
    public List<TeamRegisterManageResponseDto> getTeamJoinRequests(Long teamId, Long leaderId) {
        // 팀 존재 여부 및 팀장 확인
        Optional<TeamEntity> teamOpt = teamRepository.findById(teamId);
        if (teamOpt.isEmpty()) throw new RuntimeException("팀을 찾을 수 없습니다.");
        TeamEntity team = teamOpt.get();
        if (!team.getCreator_user_id().equals(leaderId)) {
            throw new RuntimeException("팀장이 아닙니다.");
        }

        // 해당 팀의 가입 신청 목록 조회
        List<TeamJoinRequestEntity> joinRequests = teamJoinRequestRepository.findByTeamId(teamId);
        return joinRequests.stream().map(request -> {
            Long userId = request.getUserId();

            // UserRepository.findByUserId 대신 findAllById 사용
            List<UserEntity> userList = new ArrayList<>(userRepository.findAllById(List.of(userId)));
            if (userList.isEmpty()) {
                throw new RuntimeException("사용자를 찾을 수 없습니다.");
            }
            UserEntity user = userList.get(0);
            UserStat userStat = userStatRepository.findByUserId(userId).orElse(null);

            return new TeamRegisterManageResponseDto(
                    user.getNickName(),
                    user.getPosition(),
                    userStat != null ? userStat.getOVR() : "N/A",
                    user.getUserId()
            );
        }).collect(Collectors.toList());
    }

    /**
     * ✅ 팀 가입 승인
     */
    @Override
    @Transactional
    public String acceptJoinRequest(Long teamId, Long userId, Long leaderId) {
        Optional<TeamEntity> teamOpt = teamRepository.findById(teamId);
        if (teamOpt.isEmpty()) return "TEAM_NOT_FOUND";
        if (!teamOpt.get().getCreator_user_id().equals(leaderId)) return "NOT_TEAM_LEADER";

        // ✅ 가입 신청이 실제로 존재하는지 확인
        Optional<TeamJoinRequestEntity> joinRequestOpt = teamJoinRequestRepository.findByTeamIdAndUserId(teamId, userId);
        if (joinRequestOpt.isEmpty()) {
            return "NO_JOIN_REQUEST"; // 가입 신청 목록에 해당 유저가 없음
        }

        Optional<UserProfile> userProfileOpt = userProfileRepository.findByUserId(userId);
        if (userProfileOpt.isEmpty()) return "USER_NOT_FOUND";

        UserProfile userProfile = userProfileOpt.get();
        if (userProfile.getTeam1() == null) {
            userProfile.setTeam1(teamId);
        } else if (userProfile.getTeam2() == null) {
            userProfile.setTeam2(teamId);
        } else if (userProfile.getTeam3() == null) {
            userProfile.setTeam3(teamId);
        } else {
            return "USER_ALREADY_IN_MAX_TEAMS";
        }
        userProfileRepository.save(userProfile);

        // 가입 신청 기록 삭제
        teamJoinRequestRepository.deleteByTeamIdAndUserId(teamId, userId);
        return "JOIN_ACCEPTED";
    }


    /**
     * ✅ 팀 가입 거절
     */
    @Override
    @Transactional
    public String rejectJoinRequest(Long teamId, Long userId, Long leaderId) {
        Optional<TeamEntity> teamOpt = teamRepository.findById(teamId);
        if (teamOpt.isEmpty()) return "TEAM_NOT_FOUND";
        if (!teamOpt.get().getCreator_user_id().equals(leaderId)) return "NOT_TEAM_LEADER";

        // 가입 요청 기록이 존재하는지 확인
        Optional<TeamJoinRequestEntity> joinRequestOpt = teamJoinRequestRepository.findByTeamIdAndUserId(teamId, userId);
        if (joinRequestOpt.isEmpty()) {
            return "NO_JOIN_REQUEST";  // 신청 목록에 없으면 오류 반환
        }

        // 가입 요청 기록 삭제 (거절 처리)
        teamJoinRequestRepository.deleteByTeamIdAndUserId(teamId, userId);
        return "JOIN_REJECTED";
    }

    /**
     * ✅ 팀원 목록 조회 (팀 ID 기반)
     */
    @Override
    @Transactional(readOnly = true)
    public List<TeamMemberListResponseDto> getTeamMembers(Long teamId) {
        // ✅ 팀 ID로 팀 조회
        Optional<TeamEntity> teamOpt = teamRepository.findById(teamId);
        if (teamOpt.isEmpty()) throw new RuntimeException("팀을 찾을 수 없습니다.");
        TeamEntity team = teamOpt.get();
        Long leaderId = team.getCreator_user_id();

        // ✅ 팀원 목록 조회 (UserProfile에서 team1, team2, team3 필드 기반)
        List<UserProfile> teamMembers = userProfileRepository.findByTeamId(teamId);
        return teamMembers.stream().map(userProfile -> {
            Long userId = userProfile.getUserId();
            boolean isLeader = userId.equals(leaderId);

            // ✅ 유저 정보 가져오기 (UserEntity)
            UserEntity user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

            // ✅ 유저 스탯 가져오기 (UserStat)
            UserStat userStat = userStatRepository.findByUserId(userId).orElse(null);

            return new TeamMemberListResponseDto(
                    userId,
                    isLeader,
                    user.getNickName(),
                    userStat != null ? userStat.getOVR() : "N/A"
            );
        }).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public String updateTeamInfo(TeamUpdateRequestDto updateDto, Long leaderId) {
        // 1. 팀 존재 여부 확인
        Optional<TeamEntity> teamOpt = teamRepository.findById(Long.valueOf(updateDto.getTeamID()));
        if (teamOpt.isEmpty()) {
            return "TEAM_NOT_FOUND";
        }
        TeamEntity team = teamOpt.get();

        // 2. 요청 사용자가 팀 리더인지 확인
        if (!team.getCreator_user_id().equals(leaderId)) {
            return "NOT_TEAM_LEADER";
        }

        // 3. 팀 이름 중복 여부 확인 (기존 팀 이름과 다르고, 다른 팀에서 이미 사용 중인 경우)
        if (!team.getTeamName().equals(updateDto.getTeamName())) {
            if (teamRepository.existsByTeamName(updateDto.getTeamName())) {
                return "DUPLICATE_TEAM_NAME";
            }
        }

        // 4. 팀 정보 업데이트 (팀 이름, HomeTown1, HomeTown2 등)
        team.setTeamName(updateDto.getTeamName());
        team.setHomeTown1(updateDto.getHomeTown1());
        team.setHomeTown2(updateDto.getHomeTown2());
        // 필요한 추가 필드 업데이트 (예: 팀 지역 등)
        teamRepository.save(team);

        return "TEAM_UPDATE_SUCCESS";
    }

    @Override
    @Transactional
    public String leaveTeam(Long teamId, Long userId) {
        // 1. 팀 조회 (팀 ID 기준)
        Optional<TeamEntity> teamOpt = teamRepository.findById(teamId);
        if (teamOpt.isEmpty()) {
            return "TEAM_NOT_FOUND";
        }
        TeamEntity team = teamOpt.get();

        // 2. 사용자 프로필 조회 (해당 사용자의 팀 가입 정보 확인)
        Optional<UserProfile> userProfileOpt = userProfileRepository.findByUserId(userId);
        if (userProfileOpt.isEmpty()) {
            return "USER_NOT_FOUND";
        }
        UserProfile userProfile = userProfileOpt.get();

        // 3. 사용자가 해당 팀에 속해있는지 확인 (team1, team2, team3 중 하나여야 함)
        boolean isMember = false;
        if ((userProfile.getTeam1() != null && userProfile.getTeam1().equals(teamId))
                || (userProfile.getTeam2() != null && userProfile.getTeam2().equals(teamId))
                || (userProfile.getTeam3() != null && userProfile.getTeam3().equals(teamId))) {
            isMember = true;
        }
        if (!isMember) {
            return "USER_NOT_IN_TEAM";
        }

        // 4. 팀 탈퇴 로직
        // 만약 탈퇴를 요청한 사용자가 팀 리더라면, 다른 팀원이 있는 경우 탈퇴 불가
        if (userId.equals(team.getCreator_user_id())) {
            List<UserProfile> teamMembers = userProfileRepository.findByTeamId(teamId);
            if (teamMembers.size() > 1) {
                return "TEAM_LEADER_CANNOT_LEAVE";
            }
        }

        // 5. 탈퇴 처리: 사용자 프로필에서 해당 팀 ID 삭제 (team1, team2, team3 중 일치하는 필드를 null로 설정)
        if (userProfile.getTeam1() != null && userProfile.getTeam1().equals(teamId)) {
            userProfile.setTeam1(null);
        } else if (userProfile.getTeam2() != null && userProfile.getTeam2().equals(teamId)) {
            userProfile.setTeam2(null);
        } else if (userProfile.getTeam3() != null && userProfile.getTeam3().equals(teamId)) {
            userProfile.setTeam3(null);
        }
        userProfileRepository.save(userProfile);
        return "TEAM_LEAVE_SUCCESS";

    }

    @Override
    public List<TeamSearchResponseDto> searchTeams(String searchContent) {
        // teamRepository.findByTeamNameContaining(searchContent) 메서드가 존재한다고 가정합니다.
        List<TeamEntity> teams = teamRepository.findByTeamNameContaining(searchContent);

        return teams.stream()
                .map(team -> new TeamSearchResponseDto(team.getTeamName(), team.getTeamId()))
                .collect(Collectors.toList());
    }


}
