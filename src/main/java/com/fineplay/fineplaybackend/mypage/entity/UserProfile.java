package com.fineplay.fineplaybackend.mypage.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "User_Profile")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserProfile {
    @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="User_ID", nullable = false)
    private Long userId;

    @Column(name="user_Img")
    private byte[] userImg;

    @Column(name="NickName", nullable = false)
    private String nickName;

    @Column(name="Team1", columnDefinition = "CHAR(5)") // ✅ 기존 teamName 저장 → teamId 저장하도록 변경
    private Long team1;

    @Column(name="Team2", columnDefinition = "CHAR(5)")
    private Long team2;

    @Column(name="Team3", columnDefinition = "CHAR(5)")
    private Long team3;

    @Column(name="Selected_Team", columnDefinition = "CHAR(5)")
    private Long selectedTeam;
}