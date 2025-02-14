package com.fineplay.fineplaybackend.team.repository;

import com.fineplay.fineplaybackend.team.entity.TeamEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;


@Repository
public interface TeamRepository extends JpaRepository<TeamEntity, Long> {
    boolean existsByTeamName(String teamName);
    Optional<TeamEntity> findByTeamName(String teamName);
}
