package com.fineplay.fineplaybackend.team.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TeamResponseDto {
    private String teamName;
    private String OVR;
    private String Win;
    private String Draw;
    private String Lose;
    private String HomeTown1;
    private String MemberNum;
    private String Sports;
}
