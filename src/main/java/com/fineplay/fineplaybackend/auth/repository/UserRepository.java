package com.fineplay.fineplaybackend.auth.controller.repository;

import com.fineplay.fineplaybackend.auth.entity.UserEntity;
import java.util.Date;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    boolean existsByEmail(String email);
    boolean existsByNickName(String nickName);
    boolean existsByPhoneNumber(String phoneNumber);

    UserEntity findByEmail(String email); // email은 unique 이므로 1개 또는 0개가 무조건 반환됨


    Optional<Object> findByUserId(Long userId);

    UserEntity findByRealNameAndPhoneNumberAndBirth(String realName, String phoneNumber, Date birth);

    UserEntity findByRealNameAndEmailAndPhoneNumberAndBirth(String realName, String email, String phoneNumber, Date birth);

}
