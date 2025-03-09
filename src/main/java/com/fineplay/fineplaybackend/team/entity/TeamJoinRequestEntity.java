//package com.fineplay.fineplaybackend.team.entity;
//
//import jakarta.persistence.*;
//import lombok.*;
//
//@Entity
//@Table(name = "team_join_request_oldversion")
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//@Builder
//public class TeamJoinRequestEntity {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long requestId;
//
//
//
//    @Column(nullable = false)
//    private Long userId; // 가입을 요청한 사용자
//
//    @Column(nullable = false)
//    private Long teamId;
//
//    @Column(nullable = false)
//    private String status = "PENDING"; // PENDING(대기), ACCEPTED(수락), REJECTED(거절)
//
//    public TeamJoinRequestEntity(Long userId, Long teamId) {
//        this.userId = userId;
//        this.teamId = teamId;
//        this.status = "PENDING";
//    }
//}
