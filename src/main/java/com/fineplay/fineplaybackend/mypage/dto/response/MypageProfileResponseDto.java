package com.fineplay.fineplaybackend.mypage.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MypageProfileResponseDto {
    private int status;
    private String userName;
    private String position;
    private String ovr;
    private String team1;
    private String team2;
    private String team3;
    private String selectedStat;

    // Special Stats
    private int CRO;
    private int HED;
    private int FST;
    private int ACT;
    private int OFF;
    private int TEC;
    private int COP;

    // Common Stats
    private int PAC;
    private int PAS;
    private int SPD;

    // Position Stats: FW
    private int SHO;
    private int DRV;

    // Position Stats: MF
    private int DEC;
    private int DRI;

    // Position Stats: DF
    private int TAC;
    private int BLD;

    // 스탯 이미지 경로 (또는 URL)
    private String CROImg;
    private String HEDImg;
    private String FSTImg;
    private String ACTImg;
    private String OFFImg;
    private String TECImg;
    private String COPImg;
}
