package com.fineplay.fineplaybackend.mypage.repository;

import com.fineplay.fineplaybackend.mypage.entity.UserStat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserStatRepository extends JpaRepository<UserStat, Long> {
    Optional<UserStat> findByUserId(Long userId);
}