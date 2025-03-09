package com.fineplay.fineplaybackend.team.repository;

import com.fineplay.fineplaybackend.team.entity.TeamRequestListEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamRequestListRepository extends JpaRepository<TeamRequestListEntity, Long> {
}
