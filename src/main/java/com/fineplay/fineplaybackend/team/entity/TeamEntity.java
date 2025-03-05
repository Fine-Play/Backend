package com.fineplay.fineplaybackend.team.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.*;

@Entity
@Table(name = "teamt_oldversion")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TeamEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_id", nullable = false, unique = true, columnDefinition = "INT(5) UNSIGNED ZEROFILL")
    private Long teamId;

    @Column(nullable = false, unique = true)
    private String teamName;

    private String homeTown1;
    private String homeTown2;
    private String sports;

    // 자동 가입 수락 여부
    @Column(nullable = false)
    private Boolean autoAccept = false;

    // 팀 생성자 (사용자 ID)
    @Column(nullable = false)
    private Long creator_user_id;

    private LocalDateTime createdDate;

    public TeamEntity(String teamName, String homeTown1, String homeTown2, String sports, boolean autoAccept, Long creator_user_id) {
        this.teamName = teamName;
        this.homeTown1 = homeTown1;
        this.homeTown2 = homeTown2;
        this.sports = sports;
        this.autoAccept = autoAccept;
        this.creator_user_id = creator_user_id;
        this.createdDate = LocalDateTime.now();
    }
}
