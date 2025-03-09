//package com.fineplay.fineplaybackend.team.repository;
//
//import com.fineplay.fineplaybackend.team.entity.TeamJoinRequestEntity;
//import org.springframework.data.jpa.repository.JpaRepository;
//
//import java.util.List;
//import java.util.Optional;
//
//public interface TeamJoinRequestRepository extends JpaRepository<TeamJoinRequestEntity, Long> {
//
//    // ✅ 특정 팀의 가입 요청 목록 조회 (상태 포함)
//    List<TeamJoinRequestEntity> findByTeamIdAndStatus(Long teamId, String status);
//
//    // ✅ 특정 팀의 모든 가입 요청 조회 (status 필요 없음)
//    List<TeamJoinRequestEntity> findByTeamId(Long teamId);
//
//    // ✅ 특정 팀 가입 요청 조회 (userId, teamId 기준)
//    Optional<TeamJoinRequestEntity> findByTeamIdAndUserId(Long teamId, Long userId);
//
//    // ✅ 특정 팀 가입 요청 삭제 (거절 또는 승인 후)
//    void deleteByTeamIdAndUserId(Long teamId, Long userId);
//
//    // ✅ 특정 팀의 모든 가입 요청 삭제 (팀이 삭제될 경우)
//    void deleteAllByTeamId(Long teamId);
//
//    // ✅ 특정 유저가 이미 해당 팀에 가입 신청했는지 확인
//    boolean existsByTeamIdAndUserId(Long teamId, Long userId);
//}
