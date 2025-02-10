package com.fineplay.fineplaybackend.team.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.*;

@Entity
@Table(name = "team")
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
    private Boolean autoAccept;

    // 팀 생성자 (사용자 ID)
    @Column(name = "creator_user_id", nullable = false)  // ✅ 명확한 필드명
    private Long creatorUserId;

    private LocalDateTime createdDate;

    public TeamEntity(String teamName, String homeTown1, String homeTown2, String sports, Boolean autoAccept, Long creatorUserId) {
        this.teamName = teamName;
        this.homeTown1 = homeTown1;
        this.homeTown2 = homeTown2;
        this.sports = sports;
        this.autoAccept = autoAccept;
        this.creatorUserId = creatorUserId;
        this.createdDate = LocalDateTime.now();
    }
}
