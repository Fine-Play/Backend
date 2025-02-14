package com.fineplay.fineplaybackend.team.controller;

import com.fineplay.fineplaybackend.provider.JwtProvider;
import com.fineplay.fineplaybackend.team.dto.request.CreateTeamRequestDto;
import com.fineplay.fineplaybackend.team.dto.response.TeamCreationResponseDto;
import com.fineplay.fineplaybackend.team.dto.response.TeamListResponseDto;
import com.fineplay.fineplaybackend.team.dto.response.TeamMemberListResponseDto;
import com.fineplay.fineplaybackend.team.dto.response.TeamNameDuplicateResponseDto;
import com.fineplay.fineplaybackend.team.repository.TeamRepository;
import com.fineplay.fineplaybackend.team.service.TeamService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import com.fineplay.fineplaybackend.auth.entity.UserEntity;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;


@RestController
@RequestMapping("/api/team")
@RequiredArgsConstructor
public class TeamController {

    private final TeamService teamService;
    private final JwtProvider jwtProvider;
    private final com.fineplay.fineplaybackend.auth.controller.repository.UserRepository userRepository; // ✅ 유저 정보를 조회하기 위해 추가
    private final TeamRepository teamRepository;
    /**
     * 팀 이름 중복 확인
     * GET /api/team/nameDuplicate?TeamName=팀명
     */
    @GetMapping("/nameDuplicate")
    public ResponseEntity<?> checkTeamNameDuplicate(@RequestParam("TeamName") String teamName) {
        // ✅ 전체 팀 데이터에서 중복 확인
        boolean available = !teamRepository.existsByTeamName(teamName);
        String message = available ? "사용 가능한 팀 이름" : "중복된 팀 이름";

        TeamNameDuplicateResponseDto responseDto = new TeamNameDuplicateResponseDto(available, null);
        return ResponseEntity.ok(new ApiResponse<>(200, message, responseDto));
    }

    /**
     * 팀 생성
     * POST /api/team/teamMake
     */
    @PostMapping("/teamMake")
    public ResponseEntity<?> createTeam(HttpServletRequest request,
                                        @RequestBody @Valid CreateTeamRequestDto requestDto,
                                        BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(400, "유효성 검사 실패", null));
        }

        try {
            // ✅ JWT에서 userId 가져오기
            Long userId = jwtProvider.getUserIdFromRequest(request);

            // ✅ 팀 생성 로직 실행 (userId를 직접 넘김)
            TeamCreationResponseDto creationResponse = teamService.createTeam(requestDto, userId);

            if (creationResponse.getErrCode() != null) {
                return ResponseEntity.badRequest().body(new ApiResponse<>(400, "팀 생성 실패: 중복된 팀 이름", creationResponse));
            }

            return ResponseEntity.ok(new ApiResponse<>(200, "팀 생성 성공", creationResponse));
        } catch (Exception e) {
            return ResponseEntity.status(401).body(new ApiResponse<>(401, "JWT 검증 실패", null));
        }
    }




        /**
         * 마이 팀 리스트 조회 (JWT 인증 적용)
         * GET /api/team/MyTeamList
         */
        @GetMapping("/MyTeamList")
        public ResponseEntity<TeamListResponseDto> getMyTeams(HttpServletRequest request) {
            // ✅ 요청 헤더에서 JWT 토큰 추출
            String token = jwtProvider.getTokenFromRequest(request);
            if (token == null) {
                return ResponseEntity.status(401).body(new TeamListResponseDto(401, "인증 실패: 토큰이 없습니다.", null));
            }

            // ✅ JWT 토큰에서 email 추출
            String email = jwtProvider.validateJwt(token);
            if (email == null) {
                return ResponseEntity.status(403).body(new TeamListResponseDto(403, "인증 실패: 유효하지 않은 토큰입니다.", null));
            }

            // ✅ email을 기반으로 userId 조회
            UserEntity user = userRepository.findByEmail(email);
            if (user == null) {
                return ResponseEntity.status(404).body(new TeamListResponseDto(404, "사용자를 찾을 수 없습니다.", null));
            }
            Long userId = user.getUserId(); // userId 가져오기

            // ✅ 팀 정보 조회
            return ResponseEntity.ok(teamService.getMyTeams(userId));
        }

    @GetMapping("/RegisterRequest")
    public ResponseEntity<?> requestJoinTeam(HttpServletRequest request, @RequestParam("teamId") Long teamId) {
        Long userId = jwtProvider.getUserIdFromRequest(request);
        return ResponseEntity.ok(new ApiResponse<>(200, "가입 신청 처리됨", teamService.requestJoinTeam(userId, teamId)));
    }

    /**
     * ✅ 팀 가입 신청 목록 조회 API
     * GET /api/team/TeamRegisterManage?teamId=23432
     */
    @GetMapping("/TeamRegisterManage")
    public ResponseEntity<?> getTeamJoinRequests(HttpServletRequest request, @RequestParam("teamId") Long teamId) {
        try {
            // ✅ JWT 검증 후 팀장 확인
            Long leaderId = jwtProvider.getUserIdFromRequest(request);

            return ResponseEntity.ok(teamService.getTeamJoinRequests(teamId, leaderId));
        } catch (Exception e) {
            return ResponseEntity.status(401).body("JWT 검증 실패: " + e.getMessage());
        }
    }

    @GetMapping("/TeamAccept")
    public ResponseEntity<?> acceptJoinRequest(HttpServletRequest request, @RequestParam("teamId") Long teamId, @RequestParam("userId") Long userId) {
        Long leaderId = jwtProvider.getUserIdFromRequest(request);
        return ResponseEntity.ok(new ApiResponse<>(200, "가입 승인 완료", teamService.acceptJoinRequest(teamId, userId, leaderId)));
    }

    @GetMapping("/TeamReject")
    public ResponseEntity<?> rejectJoinRequest(HttpServletRequest request, @RequestParam("teamId") Long teamId, @RequestParam("userId") Long userId) {
        Long leaderId = jwtProvider.getUserIdFromRequest(request);
        return ResponseEntity.ok(new ApiResponse<>(200, "가입 거절 완료", teamService.rejectJoinRequest(teamId, userId, leaderId)));
    }

    @GetMapping("/TeamMemberList")  // ❗ 대소문자 확인
    public ResponseEntity<?> getTeamMembers(
            HttpServletRequest request,
            @RequestParam("teamId") Long teamId) {

        // ✅ JWT 검증
        String token = jwtProvider.getTokenFromRequest(request);
        if (token == null) {
            return ResponseEntity.status(401).body(new ApiResponse<>(401, "JWT 검증 실패", null));
        }

        // ✅ 팀원 목록 조회
        List<TeamMemberListResponseDto> members = teamService.getTeamMembers(teamId);

        return ResponseEntity.ok(new ApiResponse<>(200, "팀원 목록", members));
    }
    @Getter
    @Setter
    @AllArgsConstructor
    private static class ApiResponse<T> {
        private int statusCode;
        private String responseMessage;
        private T data;
    }
    }
