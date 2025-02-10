package com.fineplay.fineplaybackend.mypage.repository;

import com.fineplay.fineplaybackend.mypage.entity.UserStatVisualization;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserStatVisualizationRepository extends JpaRepository<UserStatVisualization, Long> {
    UserStatVisualization findByUserId(Long userId);
}
