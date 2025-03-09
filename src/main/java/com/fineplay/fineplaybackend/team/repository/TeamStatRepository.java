package com.fineplay.fineplaybackend.team.repository;

import com.fineplay.fineplaybackend.team.entity.TeamStatEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamStatRepository extends JpaRepository<TeamStatEntity, Long> {
}
