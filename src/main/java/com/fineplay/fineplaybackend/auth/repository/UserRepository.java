package com.fineplay.fineplaybackend.auth.controller.repository;

import com.fineplay.fineplaybackend.auth.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    boolean existsByEmail(String email);
    boolean existsByNickName(String nickName);
    boolean existsByPhoneNumber(String phoneNumber);

    UserEntity findByEmail(String email); // email은 unique 이므로 1개 또는 0개가 무조건 반환됨

}
