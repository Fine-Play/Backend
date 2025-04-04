package com.fineplay.fineplaybackend.user.repository;

import com.fineplay.fineplaybackend.user.entity.UserTeamEntity;
import com.fineplay.fineplaybackend.user.entity.UserTeamId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserTeamRepository extends JpaRepository<UserTeamEntity, UserTeamId> {
    boolean existsByUserIdAndTeamId(Long userId, Long teamId);

    List<UserTeamEntity> findAllByTeamId(Long teamId);

    void deleteByUserIdAndTeamId(Long userId, Long teamId);

    int countByUserId(Long userId);
    int countByTeamId(Long TeamId);


    List<UserTeamEntity> findAllByUserId(Long userId);
}
