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

    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long teamId;
    private String teamName;
    private Long teamLeader;
    private String region;
    private String teamType;
    private String teamImg;
    private Integer memberNum;
    private String OVRDist;

    public TeamEntity(String teamName, String homeTown1, String homeTown2, String sports, boolean autoAccept, Long creator_user_id) {
        this.teamName = teamName;
//        this.homeTown1 = homeTown1;
//        this.homeTown2 = homeTown2;
//        this.sports = sports;
//        this.autoAccept = autoAccept;
//        this.creator_user_id = creator_user_id;
//        this.createdDate = LocalDateTime.now();
    }
}
