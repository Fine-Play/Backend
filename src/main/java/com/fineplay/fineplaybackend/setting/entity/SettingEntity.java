//package com.fineplay.fineplaybackend.setting.entity;
//
//import jakarta.persistence.*;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//import com.fineplay.fineplaybackend.auth.entity.UserEntity;
//
//@Getter
//@Setter
//@NoArgsConstructor
//@Entity
//@Table(name = "settings_oldversion")  // 테이블 이름 지정
//public class SettingEntity {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Column(nullable = false, unique = true) // 이메일은 고유값
//    private String email;
//
//    @OneToOne
//    @JoinColumn(name = "user_id", referencedColumnName = "userId") // ✅ UserEntity의 userId와 연결
//    private UserEntity user;
//
//    @Column(nullable = false)
//    private boolean matchAlarm;
//
//    @Column(nullable = false)
//    private boolean communityAlarm;
//
//    public SettingEntity(String email, boolean matchAlarm, boolean communityAlarm) {
//        this.email = email;
//        this.matchAlarm = matchAlarm;
//        this.communityAlarm = communityAlarm;
//    }
//}
