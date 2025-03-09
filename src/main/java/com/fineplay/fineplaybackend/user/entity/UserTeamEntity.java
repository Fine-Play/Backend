package com.fineplay.fineplaybackend.user.entity;

import com.fineplay.fineplaybackend.auth.entity.UserEntity;
import com.fineplay.fineplaybackend.team.entity.TeamEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name="user_team")
@Table(name="user_team")
@IdClass(UserTeamId.class)
public class UserTeamEntity {
    @Id
    private Long userId;

    @Id
    private Long teamId;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "userId")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private UserEntity user;

    @ManyToOne
    @MapsId("teamId")
    @JoinColumn(name = "teamId")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private TeamEntity team;

    private boolean isCurrent;
}
