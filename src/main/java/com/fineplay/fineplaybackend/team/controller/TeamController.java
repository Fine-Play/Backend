package com.fineplay.fineplaybackend.team.controller;

import com.fineplay.fineplaybackend.team.dto.request.CreateTeamRequestDto;
import com.fineplay.fineplaybackend.team.dto.response.TeamCreationResponseDto;
import com.fineplay.fineplaybackend.team.dto.response.TeamListResponseDto;
import com.fineplay.fineplaybackend.team.dto.response.TeamNameDuplicateResponseDto;
import com.fineplay.fineplaybackend.team.service.TeamService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/team")
@RequiredArgsConstructor
public class TeamController {

    private final TeamService teamService;

    /**
     * 팀 이름 중복 확인
     * GET /api/team/nameDuplicate?TeamName=팀명
     */
    @GetMapping("/nameDuplicate")
    public ResponseEntity<?> checkTeamNameDuplicate(@RequestParam("TeamName") String teamName) {
        boolean available = teamService.isTeamNameAvailable(teamName);
        String message = available ? "사용 가능한 팀 이름" : "중복된 팀 이름";
        TeamNameDuplicateResponseDto responseDto = new TeamNameDuplicateResponseDto(available, null);
        return ResponseEntity.ok(new ApiResponse<>(200, message, responseDto));
    }

    /**
     * 팀 생성
     * POST /api/team/teamMake
     */
    @PostMapping("/teamMake")
    public ResponseEntity<?> createTeam(@RequestBody @Valid CreateTeamRequestDto requestDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // 유효성 검사 실패시 처리 (필요에 따라 에러 응답 DTO 사용)
            return ResponseEntity.badRequest().body(new ApiResponse<>(400, "유효성 검사 실패", null));
        }
        TeamCreationResponseDto creationResponse = teamService.createTeam(requestDto);
        if (creationResponse.getErrCode() != null) {
            // 에러 발생시 (예: 중복된 팀 이름)
            return ResponseEntity.badRequest().body(new ApiResponse<>(400, "팀 생성 실패: 중복된 팀 이름", creationResponse));
        }
        return ResponseEntity.ok(new ApiResponse<>(200, "팀 생성 성공", creationResponse));

    }
    /**
     * 마이 팀 리스트 조회
     * GET /api/team/MyTeamList?userId=12345
     */
    @GetMapping("/MyTeamList")
    public ResponseEntity<?> getMyTeamList(@RequestParam("userId") Long userId) {
        TeamListResponseDto responseDto = teamService.getMyTeams(userId);
        return ResponseEntity.ok(responseDto);
    }
    // 간단한 응답 포맷 정의 (필요에 따라 기존 공통 ResponseDto를 사용할 수도 있음)
    @Getter
    @Setter
    @AllArgsConstructor
    private static class ApiResponse<T> {
        private int statusCode;
        private String responseMessage;
        private T data;
    }
}
