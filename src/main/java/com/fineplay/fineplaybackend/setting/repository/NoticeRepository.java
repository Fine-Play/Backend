package com.fineplay.fineplaybackend.setting.repository;

import com.fineplay.fineplaybackend.setting.entity.NoticeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoticeRepository extends JpaRepository<NoticeEntity, Long> {

}
