package com.fineplay.fineplaybackend.setting.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AlarmRequestDto {
    private long userId;
    private String email;  // 🔹 이메일 추가
    private boolean matchAlarm;
    private boolean communityAlarm;
}
