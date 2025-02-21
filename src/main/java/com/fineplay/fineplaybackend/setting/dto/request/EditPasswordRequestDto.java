package com.fineplay.fineplaybackend.setting.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EditPasswordRequestDto {
    private String email;
    private String oldPassword;
    private String newPassword;
}
