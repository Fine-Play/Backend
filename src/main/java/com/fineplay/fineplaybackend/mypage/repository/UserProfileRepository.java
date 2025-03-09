//package com.fineplay.fineplaybackend.mypage.repository;
//
//import com.fineplay.fineplaybackend.mypage.entity.UserProfile;
//import io.lettuce.core.dynamic.annotation.Param;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//
//import java.util.List;
//import java.util.Optional;
//
//public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
//    Optional<UserProfile> findByUserId(Long userId);
//
//    @Query("SELECT u FROM UserProfile u WHERE u.team1 = :teamId OR u.team2 = :teamId OR u.team3 = :teamId")
//    List<UserProfile> findByTeamId(@Param("teamId") Long teamId);
//}
