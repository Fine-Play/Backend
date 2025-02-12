package com.fineplay.fineplaybackend.setting.entity;

import com.fineplay.fineplaybackend.auth.entity.UserEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "terms_of_use")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TermsOfUseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String termsContent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id") // User 테이블과 연결
    private UserEntity user;
}
