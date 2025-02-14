package com.fineplay.fineplaybackend.playerSearch.repository;

import com.fineplay.fineplaybackend.playerSearch.entity.SearchHistoryEntity;

import com.fineplay.fineplaybackend.auth.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SearchHistoryRepository extends JpaRepository<SearchHistoryEntity, Long> {
    List<SearchHistoryEntity> findByUserOrderByUpdatedAtDesc(UserEntity user);
}
