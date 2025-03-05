package com.fineplay.fineplaybackend.auth.entity;

import com.fineplay.fineplaybackend.auth.dto.request.SignUpRequestDto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name="user_oldversion")
@Table(name="user_oldversion")
public class UserEntity {

    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long userId;
    private String email;
    private String realName;
    private String nickName;
    private String password;
    private String phoneNumber;
    private Date birth;
    private String position;
    private Boolean boolcert1;
    private Boolean boolcert2;
    private Boolean boolcert3;
    private Boolean boolcert4;
    private String profileImg; // 필수 아님
    private Long currentTeamId; // 필수 아님
    private String selectedStat; // 필수 아님

    public UserEntity(SignUpRequestDto dto) {
        this.email = dto.getEmail();
        this.realName = dto.getRealName();
        this.nickName = dto.getNickName();
        this.password = dto.getPassword();
        this.phoneNumber = dto.getPhoneNumber();
        this.birth = dto.getBirth();
        this.position = dto.getPosition();
        this.boolcert1 = dto.getBoolcert1();
        this.boolcert2 = dto.getBoolcert2();
        this.boolcert3 = dto.getBoolcert3();
        this.boolcert4 = dto.getBoolcert4();
    }

    public void setNickname(String nickName) {
        this.nickName = nickName;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

}
