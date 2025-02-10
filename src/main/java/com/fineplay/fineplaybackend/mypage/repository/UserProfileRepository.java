package com.fineplay.fineplaybackend.mypage.repository;

import com.fineplay.fineplaybackend.mypage.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
    Optional<UserProfile> findByUserId(Long userId);
}
