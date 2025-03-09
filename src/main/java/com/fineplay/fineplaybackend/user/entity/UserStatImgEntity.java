package com.fineplay.fineplaybackend.user.entity;

import com.fineplay.fineplaybackend.auth.entity.UserEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name="user_stat_Img")
@Table(name="user_stat_Img")
public class UserStatImgEntity {
    @Id
    private Long userId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "userId")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private UserEntity user;

    private String SPDImg;
    private String PASImg;
    private String PACImg;
    private String SHOImg;
    private String DRVImg;
    private String DRIImg;
    private String TACImg;
    private String BLDImg;
    private String CROImg;
    private String HEDImg;
    private String FSTImg;
    private String ACTImg;
    private String OFFImg;
    private String TECImg;
    private String COPImg;
}
