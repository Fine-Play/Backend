package com.fineplay.fineplaybackend.setting.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EditProfileRequestDto {
    private String email;
    private String userName;
    private String birth;
    private String nickname;
    private String position;
}
